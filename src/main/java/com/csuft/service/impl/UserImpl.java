package com.csuft.service.impl;

import com.csuft.common.MessageConst;
import com.csuft.entity.LoginTicket;
import com.csuft.entity.User;
import com.csuft.mapper.UserMapper;
import com.csuft.service.UserService;
import com.csuft.util.MD5Util;
import com.csuft.util.MailClient;
import com.csuft.util.RedisKeyUtil;
import com.csuft.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${tiankang.path.domain}")
    private String domain;

    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();

        // 对空值进行判断处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("Message", "账号不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("Message", "密码不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("Message", "邮箱不能为空!");
            return map;
        }

        // 验证账号
        User u = userMapper.selectByName(user.getUsername());
        if (u != null) {
            map.put("Message", "该账号已存在!");
            return map;
        }

        //注册用户
        user.setPassword(MD5Util.md5(user.getPassword()));
        user.setStation(0);
        userMapper.insertUser(user);

        //给用户发送激活邮件
        //激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        String url = domain + "/activation/" + user.getId() + "/" + user.getPassword();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活账号", content);

        return map;
    }


    @Override
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getStation() == 1) {
            return MessageConst.ACTIVATION_REPEAT;
        } else if (user.getPassword().equals(code)) {
            userMapper.updateStation(userId, 1);
            clearCache(userId);
            return MessageConst.ACTIVATION_SUCCESS;
        } else {
            return MessageConst.ACTIVATION_FAILURE;
        }
    }

    @Override
    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        //空值处理
        if (StringUtils.isBlank(username)) {
            map.put("Message", "用户名为空!");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("Message", "密码为空!");
            return map;
        }

        //验证账号
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("Message", "账号不存在!");
            return map;
        }
        //激活状态判断
        if (user.getStation() == 0) {
            map.put("Message", MessageConst.ACCOUNT_NOT_ACTIVATED);
            return map;
        }
        //验证密码
        //做同样的加密处理
        password = MD5Util.md5(password);
        if (!password.equals(user.getPassword())) {
            map.put("Message", "密码错误!");
            return map;
        }

        //生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(UUIDUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));

        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey, loginTicket);//redis会把这个对象序列号为字符串

        map.put("ticket", loginTicket.getTicket());

        return map;
    }

    @Override
    public void loginOut(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey, loginTicket);//覆盖
    }

    //清除缓存
    public void clearCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }
}

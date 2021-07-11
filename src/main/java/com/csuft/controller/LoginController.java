package com.csuft.controller;

import com.csuft.common.MessageConst;
import com.csuft.entity.User;
import com.csuft.service.UserService;
import com.csuft.util.RedisKeyUtil;
import com.csuft.util.ResultJSON;
import com.csuft.util.UUIDUtil;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping(path = "/register")
    public String getRegisterPage() {
        return "redirect:/pages/register";
    }

    @GetMapping(path = "/login")
    public String getLoginPage() {
        return "redirect:/pages/login.html";
    }

    /**
     * 注册
     *
     * @param model
     * @param user  用户(必须有用户名、密码、邮箱,其他的可有可无)
     * @return
     */
    @PostMapping(path = "/register")
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功,我们已经向您的邮箱发送了一封激活邮件,请尽快激活!");
            model.addAttribute("target", "/index");
            return "/operate-result";
        } else {
            model.addAttribute("Message", map.get("Message"));
            return "redirect:/pages/register.html";
        }
    }

    /**
     * 邮件中跳转
     *
     * @param model
     * @param userId
     * @param code
     * @return
     */
    @GetMapping("/activation/{userId}/{code}")
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code) {

        //查询账号是否被激活
        int result = userService.activation(userId, code);
        if (result == MessageConst.ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功,您的账号已经可以正常使用了!");
            model.addAttribute("target", "/login");
        } else if (result == MessageConst.ACTIVATION_REPEAT) {
            model.addAttribute("msg", "无效操作,该账号已经激活过了!");
            model.addAttribute("target", "/login");
        } else {
            model.addAttribute("msg", "激活失败,您提供的激活码不正确!");
            model.addAttribute("target", "/login");
        }
        return "/operate-result";
    }

    /**
     * 验证码
     *
     * @param response
     */
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response) {
        //生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        //验证码的归属者—-》颁发凭证（因为没登录）
        String kaptchaOwner = UUIDUtil.generateUUID();
        Cookie cookie = new Cookie("kaptchaOwner", kaptchaOwner);
        cookie.setMaxAge(60);//有效时间60秒
        cookie.setPath("/");//有效路径
        response.addCookie(cookie);
        //将验证码存入Redis
        String redisKey = RedisKeyUtil.getCaptchaKey(kaptchaOwner);
        redisTemplate.opsForValue().set(redisKey, text, 60, TimeUnit.SECONDS);//60秒有效

        //将图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream outputStream = response.getOutputStream();
            //这个流不用关，SpringMVC进行了管理，会自动关
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            System.out.println("响应验证码失败" + e.getMessage());
        }

    }

    /**
     * 登录
     *
     * @param messageMap
     * @param response
     * @param kaptchaOwner
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody Map<String, Object> messageMap,
                        HttpServletResponse response,
                        @CookieValue("kaptchaOwner") String kaptchaOwner) {

        String username = (String) messageMap.get("username");
        String password = (String) messageMap.get("password");
        String code = (String) messageMap.get("code");
        boolean rememberMe;
        if (messageMap.get("rememberMe") == null) {
            rememberMe = false;
        } else {
            rememberMe = true;
        }

        String kaptcha = null;
        //取验证码
        if (StringUtils.isNotBlank(kaptchaOwner)) {//判断是否失效
            String redisKey = RedisKeyUtil.getCaptchaKey(kaptchaOwner);
            kaptcha = (String) redisTemplate.opsForValue().get(redisKey);
        }
        //检测验证码
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "验证码错误!");
            return ResultJSON.getJSONString(200, false, err);
        }
        //检查账号密码
        int expiredSeconds = rememberMe ? MessageConst.REMEMBER_EXPIRED_SECONDS : MessageConst.DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username, password, expiredSeconds);

        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath("/");
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return ResultJSON.getJSONString(200, true);
        } else {
            Map<String, Object> err = new HashMap<>();
            err.put("error", map.get("Message"));
            return ResultJSON.getJSONString(200, false, err);
        }
    }

    /**
     * 凭证(在Cookie里存了)
     *
     * @param ticket
     * @return
     */
    @GetMapping("loginOut")
    public String loginOut(@CookieValue("ticket") String ticket) {
        userService.loginOut(ticket);
        return ResultJSON.getJSONString(302);
    }

}
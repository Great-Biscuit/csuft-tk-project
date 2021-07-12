package com.csuft.controller;

import com.csuft.annotation.LoginRequired;
import com.csuft.entity.User;
import com.csuft.service.UserService;
import com.csuft.util.HostHolder;
import com.csuft.util.MapTools;
import com.csuft.util.ResultJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     *
     * @return
     */
    @LoginRequired
    @GetMapping("/getUserInfo")
    @ResponseBody
    public String getUserInfo() {
        try {
            Map<String, Object> map = MapTools.objectToMap(hostHolder.getUser());
            map.remove("password");
            return ResultJSON.getJSONString(200, true, map);
        } catch (IllegalAccessException e) {
            System.out.println("获取用户信息失败: " + e.getMessage());
            return ResultJSON.getJSONString(200, false);
        }
    }

    /**
     * 更新密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param ticket      Cookie中的凭证
     * @return
     */
    @LoginRequired
    @PostMapping("/updatePassword")
    @ResponseBody
    public String updatePassword(String oldPassword, String newPassword, @CookieValue("ticket") String ticket) {
        User user = hostHolder.getUser();
        Map<String, Object> map = userService.updatePassword(user.getId(), oldPassword, newPassword);
        if (map == null || map.isEmpty()) {
            //重置密码后需要重新登录
            userService.loginOut(ticket);
            return ResultJSON.getJSONString(200, true);
        } else {
            //出现错误
            return ResultJSON.getJSONString(200, false, map);
        }
    }

}

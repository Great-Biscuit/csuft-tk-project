package com.csuft;

import com.csuft.common.MessageConst;
import com.csuft.entity.User;
import com.csuft.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class UserTest {

    @Autowired
    UserService userService;

    @Test
    public void login() {
        Map<String, Object> map = userService.login("test001", "123456",
                MessageConst.DEFAULT_EXPIRED_SECONDS);
        System.out.println(map);

    }

    @Test
    public void loginOut() {
        userService.loginOut("d85e49266a2745f9b1ca0a62fda933ea");
    }

    @Test
    public void insertUser() {
        User user = new User();
        user.setUsername("test004");
        user.setPassword("123456");
        user.setEmail("1@qq.com");

        //进行注册用户
        Map<String, Object> map = userService.register(user);
        System.out.println(map);
    }


}

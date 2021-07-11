package com.csuft.service;

import com.csuft.entity.User;

import java.util.Map;

public interface UserService {
    /**
     * 注册
     *
     * @param user
     * @return
     */
    Map<String, Object> register(User user);

    /**
     * 查询账号是否被激活
     *
     * @param userId
     * @param code
     * @return
     */
    int activation(int userId, String code);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param expiredSeconds
     * @return
     */
    Map<String, Object> login(String username, String password, int expiredSeconds);

    /**
     * 登出
     *
     * @param ticket
     */
    void loginOut(String ticket);
}

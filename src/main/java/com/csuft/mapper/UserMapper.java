package com.csuft.mapper;

import com.csuft.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectByName(String username);

    void insertUser(User user);

    User selectById(int userId);

    /**
     * 更新账号状态
     *
     * @param userId
     * @param s
     */
    void updateStation(int userId, int s);

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     */
    int updatePassword(Integer userId, String newPassword);
}

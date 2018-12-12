package com.example.dao;

import com.example.domain.User;

public interface UserDao {
    void add(User user);

    User find(String username, String password);

    /**
     * 检查用户名是否已存在。
     *
     * @param username 要检测的用户名
     * @return {@code true} 如果用户名已存在；{@code false} 如果不存在。
     */
    boolean exists(String username);
}

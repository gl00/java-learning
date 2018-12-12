package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.dao.impl.UserDaoImpl;
import com.example.domain.User;
import com.example.exception.UserAlreadyExistsException;
import com.example.utils.ServiceUtils;

/**
 * 对 Web 层提供所有的业务服务。
 */
public class BusinessServiceImpl {

    private UserDao dao = new UserDaoImpl();

    // 对 Web 层提供注册服务
    public void register(User user) throws UserAlreadyExistsException {
        if (dao.exists(user.getUsername())) {
            // 用户已存在，给 Web 层一个编译时异常，提醒 Web 层必须处理，给用户一个友好提示
            throw new UserAlreadyExistsException();
        } else {
            // 不要把明文密码存入数据库，必须先加密处理。
            user.setPassword(ServiceUtils.md5(user.getPassword()));
            dao.add(user);
        }
    }

    // 对 Web 层提供登录服务
    public User login(String username, String password) {
        // 传入的密码是明文，而数据库里的密码是加密了的
        // 所以必须先加密再查询
        String s = ServiceUtils.md5(password);
        return dao.find(username, s);
    }
}

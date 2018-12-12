package com.example.dao.impl;

import com.example.dao.UserDao;
import com.example.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {
    private static UserDao userDao;

    @BeforeAll
    static void initAll() {
        userDao = new UserDaoImpl();
    }

    @Test
    void add() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2001);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 30);

        User user = new User("1003", "ccc", "789", "ccc@gmail.com", calendar.getTime(), "强子");

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                userDao.add(user);
            }
        });
    }

    @Test
    void find() {
        User user = userDao.find("bbb", "456");
        assertNotNull(user);
    }

    @Test
    void find2() {
        User user = userDao.find("duang", "999");
        assertNull(user);
    }

    @Test
    void exists() {
        assertTrue(userDao.exists("aaa"));
    }

    @Test
    void exists2() {
        assertFalse(userDao.exists("biang"));
    }
}
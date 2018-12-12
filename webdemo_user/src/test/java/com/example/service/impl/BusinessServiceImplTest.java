package com.example.service.impl;

import com.example.domain.User;
import com.example.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class BusinessServiceImplTest {

    private static BusinessServiceImpl service;

    @BeforeAll
    static void initAll() {
        service = new BusinessServiceImpl();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void register() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1991);
        calendar.set(Calendar.MONTH, 6);
        calendar.set(Calendar.DAY_OF_MONTH, 20);

        User user = new User("1011", "xyz", "999", "xyz@gmail.com", calendar.getTime(), "包子");

        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register2() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2001);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 30);

        User user = new User("1003", "ccc", "789", "ccc@gmail.com", calendar.getTime(), "强子");

        assertThrows(UserAlreadyExistsException.class, () -> service.register(user));
    }

    @Test
    void login() {
        assertNotNull(service.login("xyz", "999"));
    }

    @Test
    void login2() {
        assertNull(service.login("woo", "123"));
    }
}
package com.example.web.UI.formbean;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录页面表单。
 */
public class LoginForm {

    private String username;
    private String password;
    private Map<String, String> errors = new HashMap<>();

    public LoginForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public boolean validate() {
        boolean ok = true;

        if (username == null || username.trim().length() == 0) {
            ok = false;
            errors.put("username", "用户名为空");
        }

        if (password == null || password.trim().length() == 0) {
            ok = false;
            errors.put("password", "密码为空");
        }

        return ok;
    }
}

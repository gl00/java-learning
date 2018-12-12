package com.example.web.UI.formbean;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * 对应注册页面表单。
 * 封装表单数据，校验表单数据，封装错误信息。
 */
public class RegisterForm {

    private String username;
    private String password;
    private String password2;
    private String email;
    private String birthday;
    private String nickname;
    private String captcha;

    // 存储错误消息，用于在页面显示给用户
    private Map<String, String> errors = new HashMap<>();

    public RegisterForm() {
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

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    // 用户名不能为空，且必须为 3-8 位字母
    // 密码不能为空，且必须为 3-8 为数字
    // 确认密码不能为空，且必须和密码一致
    // 邮箱不能为空，且必须格式正确
    // 生日可为空，但如果不为空则必须是一个有效日期
    // 昵称不能为空，且必须是汉字
    public boolean validate() {
        boolean ok = true;

        if (username == null || username.trim().length() == 0) {
            ok = false;
            errors.put("username", "用户名不能为空");
        } else if (!username.matches("[a-zA-Z]{3,8}")) {
            ok = false;
            errors.put("username", "用户名必须是 3-8 位字母");
        }

        if (password == null || password.trim().length() == 0) {
            ok = false;
            errors.put("password", "密码不能为空");
        } else if (!password.matches("\\d{3,8}")) {
            ok = false;
            errors.put("password", "密码必须是 3-8 位数字");
        }

        if (password2 == null || password2.trim().length() == 0) {
            ok = false;
            errors.put("password2", "确认密码不能为空");
        } else if (!password2.equals(password)) {
            ok = false;
            errors.put("password2", "两次密码须一致");
        }

        if (email == null || email.trim().length() == 0) {
            ok = false;
            errors.put("email", "邮箱不能为空");
        }
        // aaa@sina.com  aaa@sina.com.cn  aa_bb.cc@sina.com.cn
        else if (!email.matches("\\w+@\\w+(\\.\\w+)+")) {
            ok = false;
            errors.put("email", "邮箱格式不正确");
        }

        if (birthday != null && birthday.trim().length() > 0) {
            // 不要使用 SimpleDateFormat，因为该类对类似 1990-12-32 这种超出范围的日期会自动转换 1991-1-1
            // 这里我们希望向这种输入的日期判定为输入错误
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            format.parse(birthday);
            // 使用 beanutils 提供的日期转换器
            try {
                DateLocaleConverter dlc = new DateLocaleConverter();
                dlc.convert(birthday, "yyyy-MM-dd");
            } catch (Exception e) {
                ok = false;
                errors.put("birthday", "日期格式不对");
            }
        }

        if (nickname == null || nickname.trim().length() == 0) {
            ok = false;
            errors.put("nickname", "昵称不能为空");
        } else if (!nickname.matches("^([\u4E00-\u9FA5]+)$")) {
            ok = false;
            errors.put("nickname", "昵称必须是汉字");
        }

        return ok;
    }
}

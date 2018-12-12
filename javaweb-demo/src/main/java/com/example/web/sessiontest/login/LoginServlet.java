package com.example.web.sessiontest.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录。
 */
@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        List<User> users = Db.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // 登录成功
                // 往 session 里存入一个登录标记
                HttpSession session = req.getSession();
                session.setAttribute("user", user);

                // 跳转到首页。不能使用转发，因为浏览器地址栏不会变化。
                // 使用重定向，这样浏览器地址栏 URL 会发生变化
                resp.sendRedirect(req.getContextPath() + "/session_test/index.jsp");
                return;
            }
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;character=UTF-8");
        resp.getWriter().write("用户名或密码不正确。");
    }
}

class Db {
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User("zhangsan", "123456"));
        users.add(new User("lisi", "123456"));
        users.add(new User("wangwu", "123456"));
    }

    public static List<User> getUsers() {
        return users;
    }
}

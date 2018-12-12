package com.example.web.controller;

import com.example.domain.User;
import com.example.service.impl.BusinessServiceImpl;
import com.example.utils.WebUtils;
import com.example.web.UI.formbean.LoginForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = -7300907417344482876L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. 封装表单数据到 formbean，并校验表单字段
        LoginForm form = WebUtils.request2Bean(req, LoginForm.class);
        boolean valid = form.validate();

        // 2. 如果校验失败，跳回到表单页面，回显校验失败信息
        if (!valid) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }

        // 3. 如果校验成功，则调用 service 处理登录请求
        BusinessServiceImpl service = new BusinessServiceImpl();
        User user = service.login(form.getUsername(), form.getPassword());
        if (user != null) {
            // 登录成功
            // 保存用户信息到 session
            req.getSession().setAttribute("user", user);
            // 跳转首页。使用重定向，让客户端浏览器地址栏 URL 改变
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        req.setAttribute("message", "用户名或密码错误");
        req.getRequestDispatcher("/message.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}

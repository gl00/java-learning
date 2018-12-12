package com.example.web.controller;

import com.example.domain.User;
import com.example.exception.UserAlreadyExistsException;
import com.example.service.impl.BusinessServiceImpl;
import com.example.utils.WebUtils;
import com.example.web.UI.formbean.RegisterForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = -765146258775650409L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. 封装表单数据到 formbean，并校验表单字段
        RegisterForm form = WebUtils.request2Bean(req, RegisterForm.class);
        boolean valid = form.validate();

        // 2. 如果校验失败，跳回到表单页面，回显校验失败信息
        if (!valid) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
            return;
        }

        // 3. 如果校验成功，则调用 service 处理注册请求
        BusinessServiceImpl service = new BusinessServiceImpl();
        User user = new User();
        user.setId(WebUtils.generateID());
        WebUtils.copyBean(form, user);
        try {
            service.register(user);
            // 6. 注册成功
            req.setAttribute("message", "恭喜，注册成功！");
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
            return;
        } catch (UserAlreadyExistsException e) {
            // 4. service 处理失败因为用户已注册，则跳回注册页面，显示用户已注册的消息
            form.getErrors().put("username", "用户名已注册");
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
            // 5. service 处理失败是其他原因，跳转到网站的全局消息显示页面，为用户提供友好错误信息。
            e.printStackTrace(); // 这里有必要记录日志
            req.setAttribute("message", "服务器出现未知错误");
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}

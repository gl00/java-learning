package com.example.web.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 6553552754504658000L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
        }

        // 注销后，跳转到消息显示页面，显示注销成功，并控制消息显示页面过 3 秒后跳转首页
        String url = req.getContextPath() + "/index.jsp";
        String msg = "注销成功。浏览器将在 3 秒后自动跳转到首页。<br>如果没有跳转，请点击<a href=\"" + url + "\">这里</a>跳转。";
        msg += "<meta http-equiv=\"refresh\", content=\"3;url=" + url + "\"";

        req.setAttribute("message", msg);
        req.getRequestDispatcher("/message.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}

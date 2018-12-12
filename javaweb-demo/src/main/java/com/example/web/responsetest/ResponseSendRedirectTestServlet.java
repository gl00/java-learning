package com.example.web.responsetest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过 response 的 sendRedirect 方法实现请求重定向。
 * <p>
 * 重定向就是服务器让客户端去请求另一个资源。
 * <p>
 * 重定向的特点：
 * 1. 浏览器会向服务器发送两次请求。
 * 2. 浏览器地址栏会发生变化。
 * <p>
 * 用户登录和显示购物车时，通常用到重定向。
 */
@WebServlet(name = "ResponseSendRedirectTestServlet",
        urlPatterns = {"/ResponseSendRedirectTestServlet"})
public class ResponseSendRedirectTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 假设登录成功

        resp.setStatus(302);
        resp.setHeader("location", "index.html");

        // 便捷方法。等价于以上两条语句
//        resp.sendRedirect("index.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}

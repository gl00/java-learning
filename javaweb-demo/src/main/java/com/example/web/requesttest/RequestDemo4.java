package com.example.web.requesttest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过 Request 请求头字段 referer 防盗链。
 */
@WebServlet(name = "RequestDemo4", value = "/RequestDemo4")
public class RequestDemo4 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String referer = request.getHeader("Referer");

        // referer 等于 null 说明是直接通过 URL　访问而不是从其他 URL 跳转过来的
        // 这里不允许直接查看，而要求先看广告
        if (referer == null || !referer.startsWith("http://localhost")) {
            response.sendRedirect("request_test/request_test.html");
            return;
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String data = "本站原创内容";
        response.getWriter().write(data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}

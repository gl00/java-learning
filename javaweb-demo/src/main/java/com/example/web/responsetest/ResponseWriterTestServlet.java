package com.example.web.responsetest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * * 通过 Response.getWriter() 向客户端写入数据。编码设置以防止中文乱码。
 */
@WebServlet(name = "ResponseWriterTestServlet",
        urlPatterns = {"/ResponseWriterTestServlet"})
public class ResponseWriterTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 设置 response 使用的码表，以控制 response 以什么编码向浏览器写出数据
        response.setCharacterEncoding("UTF-8");

        // 指定浏览器以什么码表打开服务器返回的数据
//        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        // 有 setContentType 便捷方法
        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().write("欢迎");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

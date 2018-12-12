package com.example.web.responsetest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 使用响应字段 expires 控制数据缓存时间。
 */
@WebServlet(name = "ResponseExpiresTestServlet",
        urlPatterns = {"/ResponseExpiresTestServlet"})
public class ResponseExpiresTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        // 返回的数据多长时间内不过期，即让浏览器缓存数据多久

        // 从现在开始缓存一个小时
        // 时间值是从 1970 年 1 月 1 日 0 时 0 分 0 秒 0 毫秒开始经过的毫秒数
        // 所以如果要从当前时间开始后，必须先获得 currentTimeMillis
        resp.setDateHeader("Expires", System.currentTimeMillis() + 1000 * 3600);

        String data = "一些长时间不变的内容";
        resp.getWriter().write(data);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}

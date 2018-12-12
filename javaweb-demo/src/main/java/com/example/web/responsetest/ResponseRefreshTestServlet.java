package com.example.web.responsetest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * 实现自动跳转。
 */
@WebServlet(name = "ResponseRefreshTestServlet",
        urlPatterns = {"/ResponseRefreshTestServlet"})
public class ResponseRefreshTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 响应字段 refresh 设置刷新频率
//        test1(response);
        // 通过 refresh 配置跳转
//        test2(response);
        // 实际项目中一般是跳转到页面，而不是在 servlet 代码里面编写页面
        test3(request, response);
    }

    private void test2(HttpServletResponse response) throws IOException {
        response.setHeader("refresh", "3;url=index.html");
        response.getWriter()
                .write("恭喜，登录成功！浏览器将在 3 秒后跳转首页。如果没有跳转，请点击<a href=\"index.html\">首页</a>");
    }

    private void test1(HttpServletResponse response) throws IOException {
        // refresh 字段设置浏览器刷新
        response.setHeader("refresh", "3");
        String s = new Random().nextInt(10000000) + "";
        response.getWriter().write(s);
    }

    private void test3(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 这里设置的 meta 标签让浏览器自动跳转
        String msg = "<meta http-equiv=\"refresh\" content=\"3;url=index.html\">" +
                "恭喜，登录成功！浏览器将在 3 秒后跳转首页。如果没有跳转，请点击<a href=\"index.html\">首页</a>";

        getServletContext().setAttribute("message", msg);
        getServletContext().getRequestDispatcher("/response_test/message.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

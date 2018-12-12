package com.example.web.cookietest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CookieDemo2", value = "/CookieDemo2")
public class CookieDemo2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter out = resp.getWriter();
        // 删除 Cookie
        Cookie cookie = new Cookie("lastAccessTime", System.currentTimeMillis() + "");
        cookie.setMaxAge(0); // 设置 MaxAge 值为 0 就是删除 Cookie
        cookie.setPath("/javaweb-demo"); // 路径要和想要删除的 Cookie 的路径一致
        resp.addCookie(cookie);

        out.write("上次访问时间已删除");
        out.write("<br><a href=\"/javaweb-demo/CookieDemo1\">查看效果</a>");
    }
}

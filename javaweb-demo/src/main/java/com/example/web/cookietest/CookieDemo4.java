package com.example.web.cookietest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 显示商品详情。
 */
@WebServlet(name = "CookieDemo4", value = "/CookieDemo4")
public class CookieDemo4 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        // 根据用户传递过来的 id，显示响应的详细信息
        String id = req.getParameter("id");
        Map<String, Book> books = Db.getBooks();
        Book book = books.get(id);

        out.write(book.getId() + "<br>");
        out.write(book.getName() + "<br>");
        out.write(book.getAuthor() + "<br>");
        out.write(book.getDescription() + "<br>");

        out.write("<a href='/javaweb-demo/CookieDemo3'>返回商品列表</a>");

        // 构建 Cookie，回写给浏览器

        final String cookieValue = buildCookieValue(req, id);
        Cookie cookie = new Cookie("bookHistory", cookieValue);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setPath("/javaweb-demo");

        resp.addCookie(cookie);
    }

    private String buildCookieValue(HttpServletRequest req, String id) {
        StringBuilder cookieValue = new StringBuilder(id);
    /*
     假设已看过只需要保存 3 条记录
     根据原有 Cookie 可能的值有如下情况：

     原有 Cookie 值  当前查看的商品的 id    新 Cookie 值
     ----------------------------------------------
     null           1                   1
     2,5,1          1                   1,2,5
     2,5,3          1                   1,2,5
     2,5            1                   1,2,5
    */
        final int maxNumCookie = 3; // Cookie 值里最多放 3 个商品 id

        // 获取原来的 Coolie 里存的 id
        Cookie[] cookies = req.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if ("bookHistory".equals(cookies[i].getName())) {
                String value = cookies[i].getValue();
                String[] ids = value.split("\\|");
                for (int j = 0, cnt = 0; j < ids.length && cnt < maxNumCookie - 1; j++) {
                    if (!id.equals(ids[j])) {
                        // 使用逗号，在 addCookie 的时候会抛异常 java.lang.IllegalArgumentException: An invalid character [44] was present in the Cookie value
                        // 所以这里改用 |
                        cookieValue.append("|").append(ids[j]);
                        cnt++;
                    }
                }
                break;
            }
        }
        System.out.println("cookieValue=" + cookieValue);
        return cookieValue.toString();
    }
}

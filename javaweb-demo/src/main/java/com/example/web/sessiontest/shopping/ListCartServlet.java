package com.example.web.sessiontest.shopping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 显示用户购买的商品（相当于查看购物车）。
 */
@WebServlet(name = "ListCartServlet", value = "/ListCartServlet")
public class ListCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;character=UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null) {
            out.write("您没有购买任何商品。<br>");

        } else {
            out.write("您购买了如下商品：<br>");
            List<Book> bookList = (List<Book>) session.getAttribute("bookList");
            for (Book book : bookList) {
                out.write(book.getName() + "<br>");
            }
        }

        String contextPath = getServletContext().getContextPath();
        String encodeURL = resp.encodeURL(contextPath + "/ListBookServlet");
        out.write("<br><a href='" + encodeURL + "'>查看商品列表</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}

package com.example.web.sessiontest.shopping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 购买（相当于加入购物车）。
 */
@WebServlet(name = "BuyServlet", value = "/BuyServlet")
public class BuyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        Book book = Db.getBooks().get(id);

        // 保存要购买的商品到 Session，便于结账
        HttpSession session = req.getSession(false);

        // 设置的 session id Cookie 的有效期
        // 这样的话，只要 session 还有效，关闭浏览器再重新打开仍然能看到购买的商品
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(session.getMaxInactiveInterval()); // 设置为和 session 的最大生存期一样
        cookie.setPath(req.getContextPath() + "/");
        resp.addCookie(cookie);

        // 从 session 中检查是否已经存了商品数据 （购物车）
        List bookList = (List) session.getAttribute("bookList");
        if (bookList == null) {
            bookList = new ArrayList<Book>();
            session.setAttribute("bookList", bookList);
        }
        bookList.add(book);


        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;character=UTF-8");
        PrintWriter out = resp.getWriter();

        // 请求转发，浏览器地址栏不会变。刷新网页导致又购买一次
        // 所以这里不要用请求转发，而应该使用重定向
//        req.getRequestDispatcher("/ListCartServlet").forward(req, resp);
        String encodeRedirectURL = resp.encodeRedirectURL(req.getContextPath() + "/ListCartServlet");
        resp.sendRedirect(encodeRedirectURL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}

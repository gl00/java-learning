package com.example.web.controller;

import com.example.domain.Book;
import com.example.domain.Cart;
import com.example.service.BusinessService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BuyBookServlet", value = "/BuyBookServlet")
public class BuyBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        BusinessService service = new BusinessService();
        Book book = service.findBook(id);

        // 购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }

        cart.add(book);

        // 重定向是让客户端请求服务器的其他资源。
        // 但客户端无法直接访问服务器 WEB-INF 目录下的内容
        // 解决办法这里使用转发到一个 Servlet，在那个 Servlet 里再转到目的 jsp 页面。
//        resp.sendRedirect(req.getContextPath() + "/WEB-INF/jsp/listCart.jsp");
        resp.sendRedirect(req.getContextPath() + "/ListCartUIServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}

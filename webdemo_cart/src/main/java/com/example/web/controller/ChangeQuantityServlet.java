package com.example.web.controller;

import com.example.domain.Cart;
import com.example.service.BusinessService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangeQuantityServlet", value = "/ChangeQuantityServlet")
public class ChangeQuantityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        String quantity = req.getParameter("quantity");
        try {
            int n = Integer.parseInt(quantity);

            Cart cart = (Cart) req.getSession().getAttribute("cart");
            BusinessService service = new BusinessService();
            service.changeQuantity(cart, id, n);

        } catch (NumberFormatException e) {
            req.setAttribute("errormsg", "购买数量必须为正整数");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/listCart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}

package com.example.web.sessiontest.prevent_form_resubmit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SubmitFormServlet", value = "/SubmitFormServlet")
public class SubmitFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 模拟网络延时
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        boolean b = isTokenValid(req);
        if (!b) {
            System.out.println("请不要重复提交");
            return;
        }

        // 清除 token
        req.getSession().removeAttribute("token");

        String username = req.getParameter("username");
        System.out.println("向数据库中写入用户数据......" + username);
    }

    // 判断表单号是否有效。
    private boolean isTokenValid(HttpServletRequest req) {
        // 客户端的 token
        String clientToken = req.getParameter("token");
        System.out.println("clientToken: " + clientToken);
        if (clientToken == null) {
            System.out.println("没有 token");
            return false;
        }
        // 服务器端存的 token
        String serverToken = (String) req.getSession().getAttribute("token");
        System.out.println("serverToken: " + serverToken);
        if (serverToken == null) {
            return false;
        }

        return clientToken.equals(serverToken);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}

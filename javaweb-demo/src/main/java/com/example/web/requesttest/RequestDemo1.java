package com.example.web.requesttest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RequestDemo1", urlPatterns = {"/RequestDemo1"})
public class RequestDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // URL是特定类型的统一资源标识符（URI）
        // https://en.wikipedia.org/wiki/URL
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());
        // The returned URL contains a protocol, server name, port number, and server path,
        // but it does not include query string parameters.

        // Returns the query string that is contained in the request URL after the path.
        System.out.println(request.getQueryString());

        System.out.println("--------------------------------------");

        // 返回关于客户端的信息
        System.out.println(request.getRemoteUser());
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRemoteHost());
        System.out.println(request.getRemotePort());

        System.out.println(request.getMethod());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

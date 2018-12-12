package com.example.web.responsetest;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 通过 Response.getOutputStream() 向客户端写入数据。编码设置以防止中文乱码。
 */
@WebServlet(name = "ResponseOutputStreamTestServlet",
        urlPatterns = {"/ResponseOutputStreamTestServlet"})
public class ResponseOutputStreamTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 必须在写入前设置编码，否则无效

        // 告诉浏览器以什么编码方式打开返回的数据。这里的编码必须和输出数据的编码一致。
        response.setHeader("Content-Type", "text/html;charset=UTF-8");

        ServletOutputStream out = response.getOutputStream();

        // 用 HTML 的 <meta> 标签模拟一个 HTTP 响应头，来控制浏览器的行为
        // 经测试：
        // 在 Chrome Version 70.0.3538.110 (Official Build) (64-bit) 下无效
        // 在 Microsoft Edge 42.17134.1.0 下有效
//        response.getOutputStream().write("<meta web-equiv='content-type' content='text/html;charset=UTF-8'>".getBytes());

        // 输出数据，使用 UTF-8 编码
        out.write("你好世界".getBytes(StandardCharsets.UTF_8));

        // 直接写入数字，会被当做编码对应的值返回浏览器，然后浏览器查找码表中编码值为1对应的字符
        // 应该返回字符串形式
        out.write((1 + "").getBytes());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

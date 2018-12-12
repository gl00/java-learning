package com.example.web.requesttest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RequestDemo3", urlPatterns = {"/RequestDemo3"})
public class RequestDemo3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        /*
        GET 方式传入的参数需要手动转码
        注意：
        Tomcat 7 以及之前的版本默认使用的编码是 ISO-8859-1
        Tomcat 8 以及之后的版本默认使用的编码是 UTF-8，不需要转码

        参考官方文档:
        Tomcat 7: 打开 https://tomcat.apache.org/tomcat-7.0-doc/config/http.html 搜索 URIEncoding
        Tomcat 8.5: https://tomcat.apache.org/tomcat-8.5-doc/config/http.html 搜索 URIEncoding

        如果需要转码，除了可以手工转码外，还可以修改 Tomcat 的编码，也就是设置如上提到的 URIEncoding 配置
        在 Tomcat 安装目录/conf/server.xml 中的 <Connector 添加属性配置 URIEncoding="UTF-8" 或 useBodyEncodingForURI="true"
        注意: 不推荐！开发环境可能无法修改 Tomcat 配置。
         */
//        System.out.printf("username(转码前): %s%n", username);
//        username = new String(username.getBytes("ISO-8859-1"), StandardCharsets.UTF_8);
//        System.out.printf("username(转码后): %s%n", username);
        System.out.println("username: " + username);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        setCharacterEncoding 方法
        覆盖此请求体中使用的字符编码的名称。必须在读取请求参数或使用getReader（）读取输入之前调用此方法。否则，它没有任何效果。
        注意：是设置请求体中的字符编码。通过 GET 方式传递的参数不属于请求体。所以这个方法对 GET 请求无效。

        GET 请求编码需要手工转换。
         */
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String location = request.getParameter("location");
        String[] languages = request.getParameterValues("language");
        String comment = request.getParameter("comment");
        String avatar = request.getParameter("avatar");
        String id = request.getParameter("id");

        System.out.printf("username: %s%n", username);
        System.out.printf("gender: %s%n", gender);
        System.out.printf("email: %s%n", email);
        System.out.printf("location: %s%n", location);
        for (int i = 0; languages != null && i < languages.length; i++) {
            System.out.printf("language: %s%n", languages[i]);
        }
        System.out.printf("comment: %s%n", comment);
        System.out.printf("avatar: %s%n", avatar);
        System.out.printf("id: %s%n", id);


        // 前面接受 request 参数的时候使用的是 UTF-8 编码
        // 这里使用 GB2312 编码返回数据，会不会乱码？
        response.setCharacterEncoding("GB2312");
        response.setContentType("text/html;charset=GB2312");
        // 答案是不会乱码。
        // 前面使用 UTF-8 解码接受参数，和这里使用 GB2312 编码返回数据给客户端 是没有关系的
        response.getWriter().write(username);
    }
}

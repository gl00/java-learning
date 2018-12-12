package com.example.web.responsetest;

import com.example.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet(name = "ReadResourceTestServlet",
        urlPatterns = {"/ReadResourceTestServlet"})
public class ReadResourceTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("doGet in ReadResourceTestServlet");

        // Servlet 内可以通过 ServletContext 读取资源文件
//        testGetResource();

        // 普通 Java 类通过类加载器读取资源文件
        UserDao userDao = new UserDao();
        userDao.update();
    }

    private void testGetResource() throws IOException {
        // Servlet 里通过 ServletContext 读取资源文件

        // db.properties 文件位于项目/src 目录下，但这是在开发源码的时候
        // 项目部署到 Web 容器里运行的时候，src目录下的所有类都位于部署项目下的 WEB-INF/classes 目录下
        // db.properties 文件也就在该目录下
        InputStream in = getServletContext().getResourceAsStream("/WEB-INF/classes/db.properties");

        Properties props = new Properties();
        props.load(in);

        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doPost in ReadResourceTestServlet");
        doGet(request, response);
    }

}

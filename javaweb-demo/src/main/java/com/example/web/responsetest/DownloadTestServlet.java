package com.example.web.responsetest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 文件下载。
 */
@WebServlet(name = "DownloadTestServlet", urlPatterns = {"/DownloadTestServlet"})
public class DownloadTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 因为要获取文件名，所以先要获取路径
        // 如果直接使用 getResourceAsStream 方法就不能获取文件名了
        String path = getServletContext().getRealPath("/WEB-INF/download/中文文件名下载.txt");

        String filename = path.substring(path.lastIndexOf(File.separator) + 1);

        // 指定客户端以“文件下载” 的方式接收数据
//        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        // 如果文件名包含中文，则需要经过 URL 编码
        response.setHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));

        try (FileInputStream in = new FileInputStream(path);
             OutputStream out = response.getOutputStream()) {

            int len;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}

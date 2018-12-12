package com.example.web.cookietest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@WebServlet(name = "CookieDemo1", value = "/CookieDemo1")
public class CookieDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter out = resp.getWriter();

        // Cookie 里存客户上一次访问的时间

        // 获取 Cookie
        Cookie[] cookies = req.getCookies();
        boolean found = false;
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if ("lastAccessTime".equals(cookies[i].getName())) {
                found = true;
                long value = Long.parseLong(cookies[i].getValue());
                LocalDateTime localDateTime =
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
                out.write("欢饮再次访问！您上次访问的时间是：" + localDateTime);
                out.write("<br><a href=\"/javaweb-demo/CookieDemo2\">清除上次访问时间</a>");
                break;
            }
        }
        if (!found) {
            out.write("欢迎首次访问！");
        }

        // 设置 Cookie
        Cookie cookie = new Cookie("lastAccessTime", System.currentTimeMillis() + "");
        /*设置此Cookie的最大年龄（以秒为单位）。正值表示cookie将在经过许多秒后过期。
         请注意，该值是cookie过期时的最大年龄，而不是cookie的当前年龄。
         负值表示cookie不会持久存储，并在Web浏览器退出时被删除。零值会导致cookie被删除。
         */
        cookie.setMaxAge(30 * 24 * 60 * 60); // 秒为单位。保存 30 天
        /*
        指定客户端应返回cookie的cookie的路径。
        cookie对于您指定的目录中的所有页面以及该目录的子目录中的所有页面都是可见的。
        cookie的路径必须包含设置cookie的servlet，例如/ catalog，这使得cookie对/catalog下的服务器上的所有目录都可见。
        有关设置cookie路径名的更多信息，请参阅RFC 2109（可在Internet上获得）。
         */
        cookie.setPath("/javaweb-demo");
        resp.addCookie(cookie);
    }
}

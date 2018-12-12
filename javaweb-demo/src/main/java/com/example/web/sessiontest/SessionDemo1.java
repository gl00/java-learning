package com.example.web.sessiontest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SessionDemo1", value = "/SessionDemo1")
public class SessionDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        /*
        Session 生命周期

        创建：
        客户端向服务器发送请求并不会自动创建 Session，
        只有调用 getSession 方法才会创建 Session。

        消亡：
        客户端关闭浏览器并不会使 Session 关闭，Session 会在其有效期结束后被自动销毁。
        Session 有效期可以在 web.xml 中配置

            <session-config>
                <!-- 有效时间 10 分钟-->
                <session-timeout>10</session-timeout>
            </session-config>


        getSession() 方法
        返回与此请求关联的当前会话，或者如果请求没有会话，则创建一个会话。
        注：这里把 Session 译作”会话“

        第一次创建 Session 时，服务器会向客户端写入一个名为 JSESSIONID 的 Cookie，
        有了这个 Cookie，服务器建立了 Session 和客户端的关联。

        问题 1：
        自动写入的这个 Cookie 的没有设置有效期。
        浏览器关闭后就失效了。再重新打开浏览器向服务器发送请求，但此时的请求中没有 JSESSIONID Cookie，
        也就无法和原来的那个 Session（假设那个 Session 还在有效期内)建立联系。

        解决办法：
        手动设置 JSESSIONID Cookie 的最大生存期。

        问题 2：
        客户端浏览器禁用了 Cookie，无法写入 JSESSIONID
        注意：即使设置了禁用 Cookie，浏览器也不会阻止 localhost 的 Cookie，可以使用 127.0.0.1 代替 localhost

        解决办法:
        对 URL 编码
        response.encodeURL("url连接");
         */
        HttpSession session = req.getSession();
        String sessionId = session.getId();
        Cookie cookie = new Cookie("JSESSIONID", sessionId);
        cookie.setMaxAge(30 * 60); // 设置为 Session 的有效期。再大就没意义，如果 Session 失效了，那这个 JSESSIONID 也没用了。
        cookie.setPath("/javaweb-demo");
        resp.addCookie(cookie);

        session.setAttribute("product", "洗衣机");

        /*
        encodeURL 方法
        通过包含会话ID对指定的URL进行编码，或者，如果不需要编码，则返回不变的URL。
        该方法的实现包括确定是否需要在URL中编码会话ID的逻辑。例如，如果浏览器支持cookie，或者关闭会话跟踪，则不需要URL编码。
        对于健壮的会话跟踪，servlet发出的所有URL都应该通过此方法运行。否则，URL重写不能用于不支持cookie的浏览器。
        如果URL是相对的，则它始终相对于当前的HttpServletRequest。

        此方法会自动判断是否需要编码。如果编码了，则 URL 后会跟上 session id，类似这样：
        http://127.0.0.1:8080/javaweb-demo/SessionDemo2;jsessionid=DE05202314FDA154578DF42B2734DBEB
         */
        String url = resp.encodeURL("/javaweb-demo/SessionDemo2");

        out.write("<a href='" + url + "'>结账</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}

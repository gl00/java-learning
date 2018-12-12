package com.example.web.requesttest;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * 获取请求头和请求参数的值。
 */
@WebServlet(name = "RequestDemo2", urlPatterns = {"/RequestDemo2"})
public class RequestDemo2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 获取请求头字段
//        test1(request);

        // 获取请求参数的值
        test2(request);
    }

    private void test1(HttpServletRequest request) {
        /*
        getHeader 方法
        以String形式返回指定请求标头的值。如果请求未包含指定名称的标头，则此方法返回null。
        如果有多个具有相同名称的标头，则此方法返回请求中的第一个头。标头名称不区分大小写。您可以将此方法用于任何请求标头。
        */
        String accept = request.getHeader("Accept");
        String acceptEncoding = request.getHeader("Accept-Encoding");
        String userAgent = request.getHeader("User-Agent");
        System.out.println(accept);
        System.out.println(acceptEncoding);
        System.out.println(userAgent);

        System.out.println("--------------------------------------------");

        /*
        getHeaders 方法
        返回指定请求标头的所有值，作为String对象的枚举。
        某些标头（例如Accept-Language）可以由客户端发送为多个标头，每个标头具有不同的值，而不是将标头作为逗号分隔列表发送。
        如果请求未包含指定名称的任何标头，则此方法返回空Enumeration。标头名称不区分大小写。您可以将此方法用于任何请求标头。
         */
        Enumeration<String> acceptLanguages = request.getHeaders("Accept-Language");
        while (acceptLanguages.hasMoreElements()) {
            System.out.println(acceptLanguages.nextElement());
        }

        System.out.println("--------------------------------------------");

        /*
        getHeaderNames 方法
        返回此请求包含的所有标头名称的枚举。如果请求没有标头，则此方法返回空枚举。
        某些servlet容器不允许servlet使用此方法访问标头，在这种情况下，此方法返回null
         */
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.println(headerName + ": " + headerValue);
        }
    }

    private void test2(HttpServletRequest request) throws IOException {
        /*
        getParameter 方法
        以String形式返回请求参数的值，如果参数不存在，则返回null。请求参数是随请求一起发送的额外信息。
        对于HTTP servlet，参数包含在查询字符串或提交的表单数据中。
        只有在确定参数只有一个值时才应使用此方法。如果参数可能具有多个值，请使用getParameterValues（java.lang.String）。
        如果将此方法与多值参数一起使用，则返回的值等于getParameterValues返回的数组中的第一个值。
        如果参数数据是在请求主体中发送的，例如HTTP POST请求，则直接通过getInputStream()或getReader()读取主体可能会干扰此方法的执行。
         */
        System.out.println("---------- 使用 getParameter 方法 ----------");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        System.out.println(username);
        System.out.println(email);

        System.out.println("---------- 使用 getParameterValues 方法 ----------");
        /*
        getParameterValues 方法
        返回包含给定请求参数所具有的所有值的String对象数组，如果参数不存在，则返回null。如果参数具有单个值，则数组的长度为1。
         */
        String[] languages = request.getParameterValues("language");
        for (int i = 0; languages != null && i < languages.length; i++) { // null 判断放到 for 条件判断字句里
//        for (String language : languages) { // 如果 languages 为 null，则使用增强 for 循环会抛空指针异常
//            System.out.print(language + ",");
            System.out.print(languages[i] + ",");
        }
        System.out.println();

        System.out.println("---------- 使用 getParameterNames 方法 ----------");

        /*
        getParameterNames 方法
        返回String对象的Enumeration，其中包含此请求中包含的参数的名称。如果请求没有参数，则该方法返回空Enumeration。
         */
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            System.out.println(paramName + "=" + request.getParameter(paramName));
        }

        System.out.println("---------- 使用 getParameterMap 方法 ----------");
        /*
        getParameterMap 方法
        返回此请求的参数的java.util.Map。请求参数是随请求一起发送的额外信息。对于HTTP servlet，参数包含在查询字符串或发布的表单数据中。
         */
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            System.out.print(entry.getKey() + "=");
            String[] value = entry.getValue();
            for (String v : value) {
                System.out.print(v + ",");
            }
            System.out.println();
        }
        // 使用 Apache 的 commons-beanutils 工具库可以方便地把参数值 Map 设置到对应的 JavaBean 里
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
            System.out.println(user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println("---------- 使用 getInputStream 方法 ----------");
        /*
        getInputStream() 方法
        使用 ServletInputStream 以二进制数据的形式检索请求的主体（request body）。可以调用此方法和 getReader() 不能同时使用。

        注意：
        1. 此方法是获取请求体中的内容。使用 GET 请求方法传递参数是跟在 URL 后的，叫做查询参数（query string)，查询参数不是（也不属于）请求体。
        请求体一般是 POST 方式提交的表单数据。

        2. 此方法和 getReader 方法会和其他“普通” 的获取参数的方法（比如 getParameter getParameterValues 等）会互相干扰，所以二者不要一起使用。
        getParameter 方法的 Java 文档有说明：
        "如果参数数据是在请求主体中发送的，例如HTTP POST请求，则直接通过getInputStream()或getReader()读取主体可能会干扰此方法的执行。"

        经测试：
        如果在调用 getInputStream() 方法之前调用了其他获取参数的方法，则 getInputStream 方法读取不到值；
        如果在调用其他获取参数的方法前调用 getInputStream() 方法，则 getInputStream 方法能获取到值，但后面的那些其他方法获取不到值。
         */
        ServletInputStream in = request.getInputStream();
        int len;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf)) > 0) {
            System.out.println(new String(buf, 0, len));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

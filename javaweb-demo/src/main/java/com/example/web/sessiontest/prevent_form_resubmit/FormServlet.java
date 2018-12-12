package com.example.web.sessiontest.prevent_form_resubmit;

import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@WebServlet(name = "FormServlet", value = "/FormServlet")
public class FormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 产生随机数（表单号）
        String token = TokenProcessor.getInstance().generateToken();

        req.getSession().setAttribute("token", token);

        req.getRequestDispatcher("/session_test/prevent_form_resubmit/form2.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}

// 为了保证令牌处理器生成的令牌是唯一的，把此类设计为单例模式
class TokenProcessor { // 令牌
    private static TokenProcessor instance = new TokenProcessor();

    private TokenProcessor() {
    }

    public static TokenProcessor getInstance() {
        return instance;
    }

    public String generateToken() {
        // 每次产生的 token 的长度不固定，比如可能为 98756511，也可能为 321513252151925912 等
        String token = System.currentTimeMillis() + new Random().nextInt() + "";
        // 要让 token 的长度固定，可以使用 MD5 值，因为 MD5 固定是128位，即16个字节。
        /*
        https://en.wikipedia.org/wiki/MD5
        MD5消息摘要算法是广泛使用的加密散列函数，产生128位（16字节）散列值，通常以文本格式表示为32位十六进制数。
        虽然MD5最初设计用作加密哈希函数，但已发现它存在广泛的漏洞。
        它仍可用作校验和来验证数据完整性，但仅用于防止无意的损坏。它仍然适用于其他非加密目的，例如用于确定分区数据库中特定密钥的分区。
         */
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(token.getBytes());

            // md5 是一个任意的字节数组，使用 new String 来构建字符串的话，不一定在对应的编码里有对应值
//            String s = new String(md5);   // new String 使用平台默认字符编码构建字符串

            // 使用 BASE64 编码
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return base64Encoder.encode(md5);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}

package networking.cookie;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class CookiesDemo {
    public static void main(String[] args) {
//        testDefaultCookieManager();
        testCustomCookiePolicy();
    }

    private static void testDefaultCookieManager() {

        // 必须在建立连接之前设置 CookieManager

        // 空参 CookieManager 构造方法。默认使用 InMemoryCookieStore 和 CookiePolicy.ACCEPT_ORIGINAL_SERVER
        CookieManager cm = new CookieManager();
        CookieHandler.setDefault(cm);

        try {
            URL url = new URL("https://www.bing.com");

            // openConnection() 方法返回返回一个 URLConnection 实例，但并不会建立一个真正的网络连接
            // 只有调用 URLConnection 的 connect() 方法的时候才会建立真正的网络连接
            URLConnection connection = url.openConnection();

            // 建立了连接，但此时后面的 getURIs 和 getCookies 返回的都是空 List
            connection.connect();

            // 获取点 URL 的相关信息，此时后面的 getURIs 和 getCookies 返回的 List 里有数据了
            String contentType = connection.getContentType();
            System.out.println(contentType);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        displayCookieInfo(cm);
    }

    private static void displayCookieInfo(CookieManager cm) {
        CookieStore cs = cm.getCookieStore();
        List<URI> uris = cs.getURIs();

        System.out.println("URI: ");
        for (URI uri : uris) {
            System.out.println(uri.toString());
        }
        System.out.println();

        System.out.println("Cookie:");
        List<HttpCookie> cookies = cs.getCookies();
        for (HttpCookie cookie : cookies) {
            System.out.println(cookie);
        }
    }

    private static void testCustomCookiePolicy() {
        /*
        黑名单设置为：".qq.com"，则不接受来自如下 host 的 cookies
         例如：host.example.com
            domain.example.com
         但接收来自来自如下 host 的 cookies
         例如：example.com
            example.org
            myhost.example.org
         */
        String[] blacklist = new String[]{"map.baidu.com"};

        CookieManager cm = new CookieManager(null, new BlacklistCookiePolicy(blacklist));
        CookieHandler.setDefault(cm);

        try {
            URL url = new URL("https://news.baidu.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            String contentType = connection.getContentType();
            System.out.println(contentType);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        displayCookieInfo(cm);
    }

}

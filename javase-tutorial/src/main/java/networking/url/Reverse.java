package networking.url;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Reverse {
    public static void main(String[] args) {
        try {
            String stringToReverse = URLEncoder.encode("Hello World", "UTF-8");

            URL url = new URL("http://localhost:8080/servlet/ReverseServlet");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);

            try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
                out.write("string=" + stringToReverse);
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String decodedString;
                while ((decodedString = in.readLine()) != null) {
                    System.out.println(decodedString);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package networking.url;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class URLReader {
    public static void main(String[] args) {
        URL url;
        String fileName;
        try {
            url = new URL("https://www.marcobehler.com/series/4-the-perfect-java-developer-setup");
            String file = url.getFile();
            fileName = file.substring(file.lastIndexOf("/") + 1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        Path dir = Paths.get("test/out/networking/downloaded");
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Path path = dir.resolve(fileName);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
             BufferedWriter out = Files.newBufferedWriter(path)) {
            for (String l = in.readLine(); l != null; l = in.readLine()) {
                out.write(l);
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

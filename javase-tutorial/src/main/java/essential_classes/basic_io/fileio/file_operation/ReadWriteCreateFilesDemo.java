package essential_classes.basic_io.fileio.file_operation;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;

public class ReadWriteCreateFilesDemo {

    public static void main(String[] args) {
        // testCommonlyUsedMethodsForSmallFiles();

        // testBufferedIOMethodsForTextFiles();

        // testUnbufferedStreamsMethods();

        testChannelsMethods();
    }

    private static void testCommonlyUsedMethodsForSmallFiles() {
        Path file = Paths.get("test/input/xanadu.txt");
        try {
            byte[] fileArray = Files.readAllBytes(file);

            Path outputFile = Paths.get("test/out/xanaducopy.txt");
            Files.write(outputFile, fileArray);

            List<String> lines = Files.readAllLines(outputFile);
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testBufferedIOMethodsForTextFiles() {
        // Charset
        // Set<Entry<String, Charset>> entrySet =
        // Charset.availableCharsets().entrySet();
        // int i = 0;
        // for (Entry<String, Charset> entry : entrySet) {
        // System.out.println(++i + "\t" + entry.getKey() + "\t" + entry.getValue());
        // }

        // Reading
        Path file = Paths.get("test/input/file.txt");
        Charset charset = Charset.defaultCharset();
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Writing
        // Charset charset = Charset.forName("US-ASCII");
        // String s = ...;
        // try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
        // writer.write(s, 0, s.length());
        // } catch (IOException x) {
        // System.err.format("IOException: %s%n", x);
        // }
    }

    private static void testUnbufferedStreamsMethods() {
        // Reading
        Path file = Paths.get("test/input/xanadu.txt");
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Writing
        Path logfile = Paths.get("test/out/logfile.txt");
        try (OutputStream out = Files.newOutputStream(logfile,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            writer.write(LocalDateTime.now() + " Hello World!");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testChannelsMethods() {
        Path file = Paths.get("test/input/file.txt");
        // Defaults to READ
        try (SeekableByteChannel sbc = Files.newByteChannel(file)) {
            ByteBuffer buf = ByteBuffer.allocate(10);

            // Read the bytes with the proper encoding for this platform. If
            // you skip this step, you might see something that looks like
            // Chinese characters when you expect Latin-style characters.
            String encoding = System.getProperty("file.encoding");
            System.out.println("encoding: " + encoding);
            while (sbc.read(buf) > 0) {
                buf.rewind();
                System.out.print(Charset.forName(encoding).decode(buf));
                buf.flip();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

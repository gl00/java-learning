package essential_classes.basic_io.fileio.file_operation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class RandomAccessFileDemo {
    public static void main(String[] args) {
        String s = "I was here!\n";
        byte[] data = s.getBytes();
        ByteBuffer out = ByteBuffer.wrap(data);

        ByteBuffer copy = ByteBuffer.allocate(12);
        Path file = Paths.get("test/out/randomaccessfile.txt");
        try (
                // FileChannel实现了SeekableChannel接口
                FileChannel fc = FileChannel.open(file,
                        StandardOpenOption.READ, StandardOpenOption.WRITE)
                // 使用SeekableByteChannel也可以
                // SeekableByteChannel fc = Files.newByteChannel(file, StandardOpenOption.READ,
                // StandardOpenOption.WRITE);
        ) {
            // Read the first 12 bytes of the file
            int nread;
            do {
                nread = fc.read(copy);
            } while (nread != -1 && copy.hasRemaining());

            // Write "I was here!" at the beginning of the file
            fc.position(0);
            while (out.hasRemaining()) {
                fc.write(out);
            }
            out.rewind();

            // Move to the end of the file. Copy the first 12 bytes to
            // the end of the file. Then write "I was here!" again.
            long length = fc.size();
            fc.position(length - 1);
            copy.flip();
            while (copy.hasRemaining()) {
                fc.write(copy);
            }
            while (out.hasRemaining()) {
                fc.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

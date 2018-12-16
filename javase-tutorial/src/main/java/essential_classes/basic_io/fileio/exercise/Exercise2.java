package essential_classes.basic_io.fileio.exercise;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * 参考答案见CounterLetter
 * 对比参考答案，我的解答
 * 1. 没有使用面向对象的思想类编写类。
 * 2. 随机访问文件功能使用的是旧版java.io.RandomAccessFile，而不是使用新的java.nio.SeekableByteChannel
 */
public class Exercise2 {
	public static void main(String[] args) {
		String path = "test/input/datafile";
		try (RandomAccessFile raf = new RandomAccessFile(path, "r")) {
			long offset = raf.readLong();
			raf.seek(offset);
			int i = raf.readInt();
			System.out.println("The int is: " + i);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package essential_classes.basic_io.fileio.exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * 参考答案见CounterLetter
 * 对比参考答案，我的解答没有使用面向对象的思想类编写类。
 */
public class Exercise1 {

	static void usage() {
		System.err.println("usage: java MonthsInYear <character> <file>");
		System.exit(-1);
	}

	public static void main(String[] args) {
		if (args.length != 2 || args[0].length() != 1) {
			usage();
		}
		
		char c = args[0].charAt(0);
		Path file = Paths.get(args[1]);
		
		if (!Files.isRegularFile(file)) {
			System.err.println(file + " is not a regular file.");
			System.exit(-1);
		} else if (!Files.isReadable(file)) {
			System.err.println(file + " is not readable.");
			System.exit(-1);
		}

		try (BufferedReader reader = Files.newBufferedReader(file)) {
			int count = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) == c) {
						count++;
					}
				}
			}
			System.out.printf("There are %d '%s' input %s%n.", count, c, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

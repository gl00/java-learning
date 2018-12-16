package essential_classes.basic_io.io_stream.scanning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;
import java.util.Scanner;

public class ScanSum {
	public static void main(String[] args) {
		Scanner s = null;
		double sum = 0;
		try {
			s = new Scanner(new BufferedReader(new FileReader("test/input/usnumbers.txt")));
			s.useLocale(Locale.US);

			while (s.hasNext()) {
				if (s.hasNextDouble()) {
					sum += s.nextDouble();
				} else {
					s.next();
				}
			}
			System.out.println("The sum is: " + sum);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}
}

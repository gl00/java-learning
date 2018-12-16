package essential_classes.basic_io.io_stream.character_stream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CopyLines {
	public static void main(String[] args) {
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			in = new BufferedReader(new FileReader("test/input/xanadu.txt"));
			out = new PrintWriter(new FileWriter("test/out/characteroutput2.txt"));
			String s;
			while ((s = in.readLine()) != null) {
				out.println(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				out.close();
			}
		}
	}
}

package essential_classes.basic_io.io_stream.command_line;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StandardStreamsDemo {
  public static void main(String[] args) {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
      String line;
      while ((line = in.readLine()) != null) {
        if ("exit".equalsIgnoreCase(line)) {
          break;
        }
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

package essential_classes.basic_io.fileio.walk_file_tree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LinksDemo {
  public static void main(String[] args) {
//    createSymbolicLink();
//    createHardLink();
//    detectSymbolicLink();
    findTargetOfLink();
  }

  private static void createSymbolicLink() {
    Path newLink = Paths.get("test/out/newlink");
    Path target = Paths.get("test/input/xanadu.txt");

    try {
      Files.createSymbolicLink(newLink, target);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void createHardLink() {
    Path newLink = Paths.get("test/out/file-hardlink");
    Path existingFile = Paths.get("test/input/file.txt");

    try {
      Files.createLink(newLink, existingFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void detectSymbolicLink() {
    Path file = Paths.get("test/out/file-hardlink");
    boolean isSymbolicLink = Files.isSymbolicLink(file);
    System.out.printf("Is \"%s\" a symbolic link?: %s%n", file.getFileName(), isSymbolicLink);
  }

  private static void findTargetOfLink() {
    Path link = Paths.get("out/link");
    try {
      Path target = Files.readSymbolicLink(link);
      System.out.printf("Target of link '%s' is '%s'%n", link, target);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

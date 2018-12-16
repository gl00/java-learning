package essential_classes.basic_io.fileio.path_operation;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathOperationDemo {

    public static void main(String[] args) {
//        testCreatePath();
        testRetrieveInfoAboutPath();
//        testConvertPath();
//        testJoinPaths();
//        testCreatePathBetweenTwoPaths();
//        testComparePaths();
    }

    public static void testCreatePath() {
        Path p1 = Paths.get("/tmp/foo");
        Path p2 = Paths.get(URI.create("file:///Users/joe/FileTest.java"));
        System.out.println(p1);
        System.out.println(p2);
    }

    public static void testRetrieveInfoAboutPath() {
        Path path = Paths.get("C:\\Program Files (x86)\\Google\\Chrome\\Application");
        System.out.format("path.toString(): %s%n", path.toString());
        System.out.format("path.getFileName(): %s%n", path.getFileName());
        System.out.format("path.getName(0): %s%n", path.getName(0));
        System.out.format("path.getNameCount(): %d%n", path.getNameCount());
        System.out.format("path.subpath(0, 2): %s%n", path.subpath(0, 2));
        System.out.format("path.getParent(): %s%n", path.getParent());
        System.out.format("path.getRoot(): %s%n", path.getRoot());

        for (Path name : path) {
            System.out.println(name);
        }
    }

    public static void testConvertPath() {
        Path p = Paths.get("/home/logfile");
        System.out.format("p: %s%n", p);
        System.out.format("p.toUri(): %s%n", p.toUri());

        System.out.format("p.toAbsolutePath(): %s%n", p.toAbsolutePath());
        try {
            System.out.format("p.toRealPath(): %s%n", p.toRealPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testJoinPaths() {
        Path p1 = Paths.get("/home/joe/foo");
        Path p2 = Paths.get("bar");
        System.out.format("p1: %s%n", p1);
        System.out.format("p2: %s%n", p2);
        System.out.format("p1.resolve(p2): %s%n", p1.resolve(p2));
        System.out.format("p2.resolve(p1): %s%n", p2.resolve(p1));
    }

    public static void testCreatePathBetweenTwoPaths() {
        Path p1 = Paths.get("joe");
        Path p2 = Paths.get("sally");
        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);
        System.out.println("p1.relativize(p2): " + p1.relativize(p2));
        System.out.println("p2.relativize(p1): " + p2.relativize(p1));
        System.out.println();

        Path p3 = Paths.get("home");
        Path p4 = Paths.get("home/sally/bar");
        System.out.println("p3: " + p3);
        System.out.println("p4: " + p4);
        System.out.println("p3.relativize(p4): " + p3.relativize(p4));
        System.out.println("p4.relativize(p3): " + p4.relativize(p3));
        System.out.println();

        Path p5 = Paths.get("/home/user");
        Path p6 = Paths.get("scott");
        System.out.println("p5: " + p5);
        System.out.println("p6: " + p6);
        // 如果只有一个路径包含根元素，则不能构造相对路径。如果两个路径都包含根元素，则构造相对路径的能力取决于系统。
        // System.out.println("p5.relativeze(p6): " + p5.relativize(p6));
        System.out.println();

        Path p7 = Paths.get("C:\\Windows\\Logs");
        Path p8 = Paths.get("C:\\Program Files");
        System.out.println("p7: " + p7);
        System.out.println("p8: " + p8);
        System.out.println("p7.relativize(p8): " + p7.relativize(p8));
        System.out.println("p8.relativize(p7): " + p8.relativize(p7));
    }

    public static void testComparePaths() {
        Path p1 = Paths.get("/home/foo");
        Path p2 = Paths.get("foo");
        System.out.format("p1: %s%n", p1);
        System.out.format("p2: %s%n", p2);
        System.out.format("p1.equals(p2): %s%n", p1.equals(p2));
        System.out.format("p1.startsWith(p2): %s%n", p1.startsWith(p2));
        System.out.format("p2.endsWith(p2): %s%n", p1.endsWith(p2));
        System.out.format("p1.compareTo(p2): %d%n", p1.compareTo(p2));
    }

}

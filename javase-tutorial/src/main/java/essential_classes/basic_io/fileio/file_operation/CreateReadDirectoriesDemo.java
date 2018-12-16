package essential_classes.basic_io.fileio.file_operation;

import java.io.IOException;
import java.nio.file.*;

public class CreateReadDirectoriesDemo {
    public static void main(String[] args) {
//    listFileSystemRootDirectories();
//    createDirectory();
//    createTemporaryDirectory();
//    filterDirectoryListByGlob();
        writeYourOwnDirectoryFilter();
    }

    private static void listFileSystemRootDirectories() {
        Iterable<Path> rootDirs = FileSystems.getDefault().getRootDirectories();
        for (Path path : rootDirs) {
            System.out.println(path);
        }
    }

    private static void createDirectory() {
        Path path = Paths.get("test/out/a");
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //
        Path path2 = Paths.get("test/out/foo/bar/");
        try {
            Files.createDirectories(path2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createTemporaryDirectory() {
        try {
            // 在指定目录内创建临时目录
            Path temp = Files.createTempDirectory(Paths.get("test/out"), "tmp_");
            System.out.println(temp + " created.");

            // 在默认目录内创建临时目录
            // Windows 10 的临时文件存储目录为：当前用户目录/AppData/Local/Temp
            Path temp2 = Files.createTempDirectory("temp_");
            System.out.println(temp2 + " created.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void listDirectoryContents() {
        Path dir = Paths.get("test");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                System.out.println(file.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void filterDirectoryListByGlob() {
        Path dir = Paths.get("test/input");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{txt,java,class,jar}")) {
            for (Path entry : stream) {
                System.out.println(entry.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeYourOwnDirectoryFilter() {
        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) {
                try {
                    return Files.isDirectory(entry);
                } catch (Exception e) {
                    // Failed to determine if it's a directory
                    e.printStackTrace();
                    return false;
                }
            }
        };

        Path dir = Paths.get("test/input");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filter)) {
            for (Path entry : stream) {
                System.out.println(entry.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

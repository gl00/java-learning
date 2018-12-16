package essential_classes.basic_io.fileio.walk_file_tree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.ZonedDateTime;
import java.util.Random;

public class WalkFileTree {
    public static void main(String[] args) throws IOException {

        final Path start = Paths.get("C:\\files\\workspace\\java\\IdeaProjects\\javase-tutorial\\src\\main\\java\\essential_classes\\basic_io\\fileio");

        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            private final Random random = new Random();

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                check(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                check(file);
                return FileVisitResult.CONTINUE;
            }

            private void check(Path file) throws IOException {
                FileTime lastModifiedTime = Files.getLastModifiedTime(file);
                ZonedDateTime yesterday = ZonedDateTime.now().minusDays(1);
                if (lastModifiedTime.toInstant().isAfter(yesterday.toInstant())) {
                    System.out.format("%s \t %s ", file, lastModifiedTime);
                    ZonedDateTime randomDateTime = yesterday.minusDays(random.nextInt(100));
                    FileTime newFileTime = FileTime.from(randomDateTime.toInstant());
                    Files.setLastModifiedTime(file, newFileTime);
                }
            }
        });
    }
}

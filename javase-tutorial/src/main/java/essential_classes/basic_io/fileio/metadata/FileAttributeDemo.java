package essential_classes.basic_io.fileio.metadata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;

public class FileAttributeDemo {

    public static void main(String[] args) {
//        testBasicFileAttributes();
//        testSetAttribute();
//        testDosFileAttributes();
//        testPosixFileAttributes();
//        testFileOrGroupOwner();
    }

    private static void testBasicFileAttributes() {
        Path file = Paths.get("test/input/xanadu.txt");
        try {
            BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);

            System.out.println("creationTime: " + attrs.creationTime());
            System.out.println("lastAccessTime: " + attrs.lastAccessTime());
            System.out.println("lastModifiedTime: " + attrs.lastModifiedTime());

            System.out.println("isDirectory: " + attrs.isDirectory());
            System.out.println("isSymbolicLink: " + attrs.isSymbolicLink());
            System.out.println("isRegularFile: " + attrs.isRegularFile());
            System.out.println("isOther: " + attrs.isOther());
            System.out.println("fileKey: " + attrs.fileKey());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testSetAttribute() {
        Path file = Paths.get("test/input/file.txt");
        try {
            System.out.println("lastModifiedTime (original): " + Files.getLastModifiedTime(file));
            long currentTime = System.currentTimeMillis();
            FileTime fileTime = FileTime.fromMillis(currentTime);
            Files.setLastModifiedTime(file, fileTime);
            System.out.println("lastModifiedTime (updated):  " + Files.getLastModifiedTime(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testDosFileAttributes() {
        Path file = Paths.get("test/input/xanadu.txt");
        try {
            DosFileAttributes attrs = Files.readAttributes(file, DosFileAttributes.class);
            System.out.println("isReadOnly is " + attrs.isReadOnly());
            System.out.println("isHidden is " + attrs.isHidden());
            System.out.println("isArchive is " + attrs.isArchive());
            System.out.println("isSystem is " + attrs.isSystem());

            boolean isHidden = attrs.isHidden();
            Files.setAttribute(file, "dos:hidden", !isHidden);
            // attrs 里面存储的仍是旧值，必须重新查询才能获得更新后的值
//            System.out.println("isHidden is (updated) " + attrs.isHidden());
            System.out.println("isHidden is (updated) " + Files.isHidden(file));
        } catch (UnsupportedOperationException e) {
            System.err.println("DOS file attributes not supported:" + e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testPosixFileAttributes() {
        Path path = Paths.get("test/input/xanadu.txt");
        try {
            PosixFileAttributes attrs = Files.readAttributes(path, PosixFileAttributes.class);
            if (attrs == null) {
                System.out.println("The returned PosixFileAttributes is NULL");
            } else {
                System.out.println("group: " + attrs.group());
                System.out.println("owner: " + attrs.owner());
                System.out.println("permissions: ");
                for (PosixFilePermission permission : attrs.permissions()) {
                    System.out.println(permission);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testFileOrGroupOwner() {
        Path file = Paths.get("test/input/xanadu.txt");
        try {
            UserPrincipal owner = file.getFileSystem().getUserPrincipalLookupService()
                    .lookupPrincipalByName("gregg");
            System.out.println(owner);
            GroupPrincipal group = file.getFileSystem().getUserPrincipalLookupService()
                    .lookupPrincipalByGroupName("gregg");
            System.out.println(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

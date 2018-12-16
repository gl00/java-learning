package reflect.classes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class ClassSpy {

    public static void main(String[] args) {
        try {
            Class<?> c = Class.forName(args[0]);
            System.out.printf("Class:%n  %s%n%n", c.getCanonicalName());

            Package p = c.getPackage();
            System.out.printf("Package:%n  %s%n%n", p != null ? p.getName() : "-- No Package --");

            for (int i = 1; i < args.length; i++) {
                switch (ClassMember.valueOf(args[i])) {
                    case CONSTUCTOR:
                        printMembers(c.getConstructors(), "Constructor");
                        break;
                    case FIELD:
                        printMembers(c.getFields(), "Fields");
                        break;
                    case METHOD:
                        printMembers(c.getMethods(), "Methods");
                        break;
                    case CLASS:
                        printClasses(c);
                        break;
                    case ALL:
                        printMembers(c.getConstructors(), "Constructor");
                        printMembers(c.getFields(), "Fields");
                        printMembers(c.getMethods(), "Methods");
                        printClasses(c);
                        break;
                    default:
                        assert false;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printMembers(Member[] mbrs, String s) {
        System.out.printf("%s:%n", s);
        for (Member mbr : mbrs) {
            if (mbr instanceof Field) {
                System.out.printf("  %s%n", ((Field) mbr).toGenericString());
            } else if (mbr instanceof Constructor) {
                System.out.printf("  %s%n", ((Constructor) mbr).toGenericString());
            } else if (mbr instanceof Method) {
                System.out.printf("  %s%n", ((Method) mbr).toGenericString());
            }
        }
        if (mbrs.length == 0) {
            System.out.printf("  -- %s --%n", s);
        }
        System.out.printf("%n");
    }

    private static void printClasses(Class<?> c) {
        System.out.printf("Classes:%n");
        Class<?>[] clss = c.getClasses();
        for (Class<?> cls : clss) {
            System.out.printf("  %s%n", cls.getCanonicalName());
        }
        if (clss.length == 0) {
            System.out.printf("  -- No member interfaces, classes, or enums --%n");
        }
        System.out.printf("%n");
    }

    private enum ClassMember {CONSTUCTOR, FIELD, METHOD, CLASS, ALL}

}

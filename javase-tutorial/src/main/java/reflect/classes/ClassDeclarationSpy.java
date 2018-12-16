package reflect.classes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class ClassDeclarationSpy {

    public static void main(String[] args) {
        try {
            Class<?> c = Class.forName(args[0]);
            System.out.printf("Class:%n  %s%n%n", c.getCanonicalName());
            System.out.printf("Modifiers:%n  %s%n%n", Modifier.toString(c.getModifiers()));

            System.out.println("Type parameters:%n");
            TypeVariable<? extends Class<?>>[] tv = c.getTypeParameters();
            if (tv.length != 0) {
                System.out.printf("  ");
                for (TypeVariable t : tv) {
                    System.out.printf("%s", t.getName());
                }
                System.out.printf("%n%n");
            } else {
                System.out.printf("  -- No Implemented Interfaces -- %n%n");
            }

            System.out.printf("Inheritance Path:%n");
            List<Class> list = new ArrayList<>();
            printAncestor(c, list);
            if (list.size() != 0) {
                for (Class<?> cl : list) {
                    System.out.printf("  %s%n", cl.getCanonicalName());
                }
                System.out.printf("%n");
            } else {
                System.out.printf("  -- No Super Classes --%n%n");
            }

            System.out.printf("Annotations:%n");
            Annotation[] ann = c.getAnnotations();
            if (ann.length != 0) {
                for (Annotation a : ann) {
                    System.out.printf("  %s%n", a.toString());
                }
                System.out.printf("%n");
            } else {
                System.out.printf("  -- No Annotations --%n%n");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printAncestor(Class<?> c, List<Class> list) {
        Class<?> ancestor = c.getSuperclass();
        if (ancestor != null) {
            list.add(ancestor);
            printAncestor(ancestor, list);
        }
    }
}

package reflect.classes;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class RetrievingClassObjects {

    public static void main(String[] args) {
        //        getClassDemo();
        //        classDemo();
        //        classForNameDemo();
        //        typeFieldForPrimitiveTypeWrappers();
        methodsThatReturnClasses();
    }

    private static void getClassDemo() {
        Class<? extends String> class1 = "Hello".getClass();

        Class<? extends InputStream> class2 = System.in.getClass();

        Class<? extends Sex> class3 = Sex.MALE.getClass();

        byte[] bytes = new byte[1024];
        Class<? extends byte[]> class4 = bytes.getClass();

        Set<String> s = new HashSet<>();
        Class<? extends Set> class5 = s.getClass();

        System.out.println(class1);
        System.out.println(class2);
        System.out.println(class3);
        System.out.println(class4);
        System.out.println(class5);
    }

    private static void classDemo() {
        Class<Boolean> class1 = boolean.class;

        Class<Boolean> class2 = Boolean.class;

        Class<Integer> class3 = int.class;

        Class<PrintStream> class4 = PrintStream.class;

        Class<Sex> class5 = Sex.class;

        Class<int[][][]> class6 = int[][][].class;

        Class<Void> class7 = void.class;

        System.out.println(class1);
        System.out.println(class2);
        System.out.println(class3);
        System.out.println(class4);
        System.out.println(class5);
        System.out.println(class6);
        System.out.println(class7);
    }

    /*
       如果类的完全限定名称可用，则可以使用静态方法Class.forName（）获取相应的Class。这不能用于原始类型。
       */
    private static void classForNameDemo() {
        try {
            Class<?> aClass = Class.forName("collections.aggregate_operations.Person");

            Class<?> cDoubleArray = Class.forName("[D");
            Class<?> cStringArray = Class.forName("[[Ljava.lang.String;");

            //            Class<?> aBoolean = Class.forName("boolean"); // 错误，不能用于基本数据类型。
            Class<?> aClass1 = Class.forName("java.lang.Boolean");
            //            Class<?> anInt = Class.forName("int");    // 错误，不能用于基本数据类型
            Class<?> aClass2 = Class.forName("java.io.PrintStream");
            Class<?> aClass3 = Class.forName("reflect.classes.RetrievingClassObjects$Sex");
            Class<?> aClass4 = Class.forName("[[[I");

            System.out.println(aClass);
            System.out.println(cDoubleArray);
            System.out.println(cStringArray);

            //            System.out.println(aBoolean);
            //            System.out.println(anInt);
            System.out.println(aClass2);
            System.out.println(aClass3);
            System.out.println(aClass4);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void typeFieldForPrimitiveTypeWrappers() {
        Class<Double> doubleClass = Double.TYPE;

        Class<Void> voidClass = Void.TYPE;

        System.out.println(doubleClass);
        System.out.println(voidClass);
    }

    public static void methodsThatReturnClasses() {
        // 返回给定类的父类
        Class<? super String> c = String.class.getSuperclass();
        System.out.println(c);
        System.out.println();

        // 返回作为类成员的所有公共类，接口和枚举，包括继承的成员
        Class<?>[] classes = System.class.getClasses();
        for (int i = 0; i < classes.length; i++) {
            System.out.println(classes[i].toString());
        }
        System.out.println();

        // 返回在此类中显式声明的所有类接口和枚举。
        Class<?>[] declaredClasses = Character.class.getDeclaredClasses();
        for (int i = 0; i < declaredClasses.length; i++) {
            System.out.println(declaredClasses[i]);
        }
        System.out.println();

        try {
            Field f = System.class.getField("out");
            // 返回声明此成员的Class。
            Class<?> c2 = f.getDeclaringClass();
            System.out.println(c2);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        System.out.println();

        // 返回内部类的直接包含类
        Class<?> enclosingClass = Thread.State.class.getEnclosingClass();
        System.out.println(enclosingClass);
    }

    private enum Sex {MALE, FEMALE, OTHER}

}

package reflect.classes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class Cls {
    private Cls() {
        System.out.println("Private constructor of class Cls.");
    }
}

public class ClassTrouble {
    public static void main(String[] args) {
        Class<?> c = null;
        try {
            c = Class.forName("reflect.classes.Cls");

            // Cls 类的构造方法是私有的，所以会抛非法访问权限异常
//            c.newInstance(); // java.lang.IllegalAccessException

            // 解决办法是利用 java.lang.reflect.AccessibleObject 类中的修改访问权限的方法。
            // 但 java.lang.Class 类不是 AccessibleObject 的子类，唯一的解决办法是
            // 使用 Constructor.newInstance() , Constructor 是 AccessibleObject 的子类

            // 另外，Class 的 newInstance() 方法也过时了，应该先获得对应的构造方法，在对构造方法调用 newInstance
            // 总之，想要通过反射创建对象，使用 Constructor 的 newInstance 方法就对了
            Constructor<?> ctr = c.getDeclaredConstructor();

            // 抑制 Java 的访问权限检查，这样就可以通过 ctr 的 newInstance 方法调用 c 的任何构造方法了，即使构造方法是私有的
            ctr.setAccessible(true);
            ctr.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}


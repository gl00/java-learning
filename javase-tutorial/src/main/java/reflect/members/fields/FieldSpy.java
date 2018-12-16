package reflect.members.fields;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Obtaining Field Types demo
 * @param <T>
 */
public class FieldSpy<T> {
    public boolean[][] b = {{false, false}, {true, true}};
    public String name = "Alice";
    public List<Integer> list;
    public T val;

    private int num = 100;

    public static void main(String[] args) {
        try {
            Class<?> c = Class.forName(args[0]);

            // getField 方法只能获取 public 的 fields，包括从父类或父接口中继承过来的 fields
//            Field f = c.getField(args[1]);
            // getDeclaredField 方法获取在该类中声明的 fields，不包括从父类或父接口继承过来的
            Field f = c.getDeclaredField(args[1]);

            System.out.printf("Type: %s%n", f.getType());
            System.out.printf("GenericType: %s%n", f.getGenericType());



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}

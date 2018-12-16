package reflect.members.fields;

import java.lang.reflect.Field;

public class FieldTrouble {
    private Integer val;

    public static void main(String[] args) {
        FieldTrouble ft = new FieldTrouble();
        try {
            Class<? extends FieldTrouble> c = ft.getClass();
            Field f = c.getDeclaredField("val");

            /*
            FieldTrouble示例将生成IllegalArgumentException。
            调用Field.setInt（）以设置具有基本类型值的引用类型为Integer的字段。
            在非反射等价的整数val = 42中，编译器将原始类型42转换（或框）为引用类型为新的整数（42），
            以便其类型检查将接受该语句。使用反射时，类型检查仅在运行时进行，因此无法对值进行装箱。
             */
            // java.lang.IllegalArgumentException: Can not set java.lang.Integer field reflect.members.fields.FieldTrouble.val to (int)42
//            f.setInt(ft, 42);
            // 解决办法
//            f.set(ft, Integer.valueOf(42));
            f.set(ft, 11); // 这条语句可以执行。是因为 set 方法会自动装箱么？应该是

//            System.out.println(f.getInt(ft)); // IllegalArgumentException getInt也不会自动装箱
            System.out.println(f.get(ft));

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

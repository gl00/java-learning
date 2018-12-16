package reflect.members.fields;

import java.lang.reflect.Field;


public class FieldTroubleToo {
    private final boolean b = true;

    public static void main(String[] args) {
        FieldTroubleToo ft = new FieldTroubleToo();
        Class<? extends FieldTroubleToo> c = ft.getClass();
        try {
            Field f = c.getDeclaredField("b");

            // 声明为 final 的 field 的值不能被修改
            // 提示：存在访问限制，阻止在初始化类之后设置最终字段。但是，Field 扩展 AccessibleObject，它提供了禁止此检查的功能。
            f.setAccessible(true); // 这样就可以了
            // 如果AccessibleObject.setAccessible（）成功，则对此字段值的后续操作将不会失败。这可能会产生意想不到的副作用;
            // 例如，有时原始值将继续由应用程序的某些部分使用，即使该值已被修改。只有安全上下文允许操作，AccessibleObject.setAccessible（）才会成功。

            // java.lang.IllegalAccessException: Can not set final boolean field reflect.members.fields.FieldTroubleToo.b to (boolean)false
            f.setBoolean(ft, Boolean.FALSE);

            System.out.println(f.get(ft));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}

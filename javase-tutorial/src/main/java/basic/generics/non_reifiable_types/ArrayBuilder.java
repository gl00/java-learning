package basic.generics.non_reifiable_types;

import java.util.Arrays;
import java.util.List;

public class ArrayBuilder {

    public static <T> void addToList(List<T> listArg, T... elements) {
        for (T x : elements) {
            listArg.add(x);
        }
    }

    public static void faultyMethod(List<String>... l) {
        /*
        与varargs形式参数l的参数化类型匹配的值可以分配给变量objectArray，因此可以分配给l。
        但是，编译器不会在此语句中生成未经检查的警告。
        编译器在将varargs形式参数List <String> ... l转换为形式参数List [] l时已生成警告。
        本声明有效;变量l的类型为List []，它是Object []的子类型。
        */
        Object[] objectArray = l; // Valid  // 这一语句可能会引入堆积污染

        /*
        因此，如果将任何类型的List对象分配给objectArray数组的任何数组组件，编译器不会发出警告或错误，如下所示：
            objectArray[0] = Arrays.asList(42);
        此语句使用包含一个Integer类型的对象的List对象分配objectArray数组的第一个数组组件。
        */
        objectArray[0] = Arrays.asList(42); // 如果直接使用l[0] = Arrays.asList(42);这条语句就无法编译


        String s = l[0].get(0); // ClassCastException thrown here
        System.out.println(s);
    }

}
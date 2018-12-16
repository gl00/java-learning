package reflect.classes;

import java.lang.reflect.Method;

public class ClassWarning {
    void m() {
        try {
            Class<ClassWarning> c = ClassWarning.class;

            // 声明 c 为一个原始类型（raw type，即没有使用泛型而直接使用普通类型），
            // 那编译此类的时候后面的调用 getMethod 语句会有警告
//            Class c = ClassWarning.class;

            // getMethod 方法签名中有可选参数 Class<?>... parameterTypes
            // 如果 c 没有使用泛型，那编译时这条语句就会有 ”未检查“ 警告
            Method m = c.getMethod("m");

            // 解决办法是：声明 c 的时候使用泛型
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

package basic.generics.wildcards;

import java.util.List;

public class WildcardErrorBad {
    /*
    在这个例子中，代码正在尝试一个不安全的操作。例如，请考虑以下对swapFirst方法的调用：

      List<Integer> li = Arrays.asList(1, 2, 3);
      List<Double>  ld = Arrays.asList(10.10, 20.20, 30.30);
      swapFirst(li, ld);

    List<Integer>和List<Double>都满足List<? extends Number> 的条件，从Integer值列表中获取项目并尝试将其放入Double值列表显然不正确。

    没有帮助方法来解决这个问题，因为代码是根本错误的。
     */
    void swapFirst(List<? extends Number> l1, List<? extends Number> l2) {
        Number temp = l1.get(0);
//    l1.set(0, l2.get(0));
//    l2.set(0, temp);
    }
}

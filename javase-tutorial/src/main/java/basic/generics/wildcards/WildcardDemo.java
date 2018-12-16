package basic.generics.wildcards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class WildcardDemo {
  public static void main(String[] args) {
    // 非严格意义上，可以把上限通配符泛型看成是只读的。
    List<? extends Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

    // 因为

    // 无法添加或更改元素
//    list.addBox(Integer.valueOf(1)); // error
//    list.set(0, 10); // error

    // 但可以执行以下操作：

    // 可以添加null
    list.add(null);

    // 可以删除
    list.remove(0);

    // 可以获取迭代器
    for (ListIterator<? extends Integer> lit = list.listIterator(); lit.hasNext(); ) {
      Integer i = lit.next();
      System.out.println(i);
    }

    // 可以clear
    list.clear();
  }
}

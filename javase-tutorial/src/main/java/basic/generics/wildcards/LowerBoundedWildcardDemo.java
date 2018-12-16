package basic.generics.wildcards;

import java.util.ArrayList;
import java.util.List;

public class LowerBoundedWildcardDemo {

    // 假设您要编写一个将Integer对象放入列表的方法。为了最大限度地提高灵活性，您希望该方法可以处理
    // List <Integer>，List <Number>和List <Object> - 任何可以保存Integer值的方法。
    public static void addNumbers(List<? super Integer> list) {
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
    }

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        addNumbers(list);
        System.out.println(list);
    }

}

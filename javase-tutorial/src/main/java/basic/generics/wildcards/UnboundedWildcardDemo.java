package basic.generics.wildcards;

import java.util.Arrays;
import java.util.List;

public class UnboundedWildcardDemo {

    public static void printList(List<?> list) {
        for (Object elem : list) {
            System.out.print(elem + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 因为对于任何具体类型A，List <MALE>是List <？>的子类型，您可以使用printList打印任何类型的列表：
        List<Integer> li = Arrays.asList(1, 2, 3);
        List<String> ls = Arrays.asList("one", "two", "three");
        printList(li);
        printList(ls);
    }

}

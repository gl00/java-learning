package collections.interfaces.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class ListDemo {
    public static void main(String[] args) {
//        testIterators();
        testRangeViewOperation();
//        testListAlgorithms();
    }

    private static List<String> initTestData() {
        List<String> list = new ArrayList<>();
        list.add("Monday");
        list.add("Tuesday");
        list.add("Wednesday");
        list.add("Thursday");
        list.add("Friday");
        list.add("Saturday");
        list.add("Sunday");
        return list;
    }

    private static void testIterators() {
        List<String> list = initTestData();
        System.out.printf("%30s: %s%n", "Original list", list);
        for (ListIterator<String> it = list.listIterator(list.size()); it.hasPrevious(); ) {
            // 请注意，Iterator.remove是在迭代期间修改集合的唯一安全方法;如果在迭代进行过程中以任何其他方式修改基础集合，则行为未指定。
//            if (it.previousIndex() % 2 != 0) { // 错误。 remove 后，索引也会变化。这条语句会导致死循环。
//                it.remove();
//            }
            if (it.previous().equalsIgnoreCase("Monday")) {
                it.remove();
            }
        }
        System.out.printf("%30s: %s%n", "After remove", list);
    }


    private static void testRangeViewOperation() {
        List<String> list = initTestData();
        System.out.format("%30s: %s%n", "Original List", list);

        List<String> subListView = list.subList(3, 6);
        System.out.format("%30s: %s%n", "list.subList(3, 6)", subListView);

        // 子 List 的修改会反映到原 List 上，反之亦然。
        // sublist 方法返回的是原 List 的子视图，不是返回原 List 的部分拷贝。
        // 对子 List 修改就是直接对原 List 修改。

        System.out.println();
        System.out.println("Modify sub list view: ");
        System.out.println();


        subListView.add("Someday");
        System.out.println("subListView.add(\"Someday\")");
        System.out.format("%30s: %s%n", "Original List", list);
        System.out.format("%30s: %s%n", "sublist view", subListView);
        System.out.println();

        list.set(5, "aaa");
        System.out.println("list.set(5, \"aaa\")");
        System.out.format("%30s: %s%n", "Original List", list);
        System.out.format("%30s: %s%n", "sublist view", subListView);
        System.out.println();



        /*
        The semantics of the list returned by this method become undefined if the backing list (i.e., this list)
         is structurally modified input any way other than via the returned list.
          (Structural modifications are those that change the size of this list, or otherwise perturb it input such
           a fashion that iterations input progress may yield incorrect results.)


        如果支持列表（即，此列表）在结构上以除了返回列表之外的任何方式进行修改，则此方法返回的列表的语义将变为未定义。
        （结构修改是那些改变了这个列表的大小，或以其他方式扰乱它的方式，正在进行的迭代可能会产生不正确的结果。）
         */

        // 通过子 List 改变了 List 的 size 大小，没问题
        subListView.remove(0);
        System.out.println("subListView.remove(0)");
        System.out.format("%30s: %s%n", "Original List", list);
        System.out.format("%30s: %s%n", "sublist view", subListView);
        System.out.println();

        // 通过原 List 改变了 List 的 size 大小
        list.add("XYZ");
        System.out.println("list.add(\"XYZ\")");
        System.out.format("%30s: %s%n", "Original List", list);
        // 再使用子 List 就会报错
        // 抛异常 java.util.ConcurrentModificationException
        System.out.format("%30s: %s%n", "sublist view", subListView);
        System.out.println();

        // 通过原 List 改变了 List 的 size 大小
        list.remove(0); // 不管 remove 的元素是不是在子 List 范围内。
        System.out.println("list.remove(5)");
        System.out.format("%30s: %s%n", "Original List", list);
        // 再使用子 List 就会报错
        // 抛异常 java.util.ConcurrentModificationException
        System.out.format("%30s: %s%n", "sublist view", subListView);
        System.out.println();
    }

    private static void testListAlgorithms() {
        List<String> list = initTestData();

        System.out.println(list);
        Collections.rotate(list, 1);
        System.out.println(list);
    }
}

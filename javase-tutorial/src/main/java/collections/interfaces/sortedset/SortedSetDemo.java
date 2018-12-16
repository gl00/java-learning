package collections.interfaces.sortedset;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetDemo {
    public static void main(String[] args) {
        //        testRangeViewOperations();
        testRangeViewOperations1();
    }

    private static void testRangeViewOperations1() {
        SortedSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);
        }
        System.out.printf("%20s: %s%n", "set", set);

        SortedSet<Integer> subSet = set.subSet(5, 100);
        System.out.printf("%20s: %s%n", "subSet", subSet);

        System.out.println("subSet.remove(6):");
        subSet.remove(6);
        System.out.printf("%20s: %s%n", "set", set);
        System.out.printf("%20s: %s%n", "subSet", subSet);

        // The range-view operations are somewhat analogous to those provided by the List interface,
        // but there is one big difference. Range views of a sorted set remain valid even if the
        // backing sorted set is modified directly. This is feasible because the endpoints of a range
        // view of a sorted set are absolute points input the element space rather than specific elements
        // input the backing collection, as is the case for lists. MALE range-view of a sorted set is really
        // just a window onto whatever portion of the set lies input the designated part of the element space.
        // Changes to the range-view write back to the backing sorted set and vice versa. Thus, it's okay
        // to use range-views on sorted sets for long periods of time, unlike range-views on lists.

        // 对原 Set 添加/删除元素，改变了 size 大小，不影响 subSet 的使用。
        // 这点和 List 不同！
        set.add(10);
        System.out.println("set.add(10): ");
        System.out.printf("%20s: %s%n", "set", set);
        System.out.printf("%20s: %s%n", "subSet", subSet);

        set.remove(7);
        System.out.println("set.remove(7): ");
        System.out.printf("%20s: %s%n", "set", set);
        System.out.printf("%20s: %s%n", "subSet", subSet);

        subSet.remove(6);
        System.out.println("subSet.remove(6): ");
        System.out.printf("%20s: %s%n", "set", set);
        System.out.printf("%20s: %s%n", "subSet", subSet);
    }

    private static void testRangeViewOperations() {
        SortedSet<String> dictionary = new TreeSet<>();

        try (Scanner scanner = new Scanner(new File("test/input/dictionary.txt"))) {
            while (scanner.hasNext()) {
                dictionary.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println(dictionary.size() + " distinct words.");

        // subSet 的使用

        for (char c = 'a'; c <= 'z'; ) {
            String from = String.valueOf(c);
            c++;
            String to = String.valueOf(c);
            System.out.println(from + ": " + dictionary.subSet(from, to).size());
        }

        int count = dictionary.subSet("doorbell", "pickle").size();
        System.out.println("From \"doorbell\" (inclusive) to \"pickle\" (exclusive): " + count);

        /*
        假设您要查看包含其两个端点的封闭间隔，而不是打开的间隔。如果元素类型允许计算元素空间中给定值的后继，
        则只需从subEndpoint请求subSet（highEndpoint）。虽然它并不完全明显，
        但String的自然排序中的字符串s的后继是s +“\0” - 即附加了null 字符的s。
         */
        count = dictionary.subSet("doorbell", "pickle\0").size();
        System.out.println("From \"doorbell\" (inclusive) to \"pickle\" (inclusive): " + count);

        /*
        可以使用类似的技术来查看开放间隔，该间隔既不包含端点也不包含端点。
        从lowEndpoint到highEndpoint的开放间隔视图是从后继（lowEndpoint）到highEndpoint的半开放间隔。
        使用以下内容计算“门铃”和“泡菜”之间的单词数，不包括两者。
         */
        count = dictionary.subSet("doorbell\0", "pickle").size();
        System.out.println("From \"doorbell\" (exclusive) to \"pickle\" (exclusive): " + count);

        // headSet 和 tailSet 的使用

        System.out.println("a-m (both inclusive): " + dictionary.headSet("n").size());
        System.out.println("n-z (both inclusive): " + dictionary.tailSet("n").size());
    }
}

package collections.interfaces.set;

import java.util.HashSet;
import java.util.Set;

public class SetDemo {

    public static void main(String[] args) {
        testBulkOperations();
    }

    private static void testBulkOperations() {
        Set<Integer> s1 = new HashSet<>();
        Set<Integer> s2 = new HashSet<>();
        Set<Integer> s3 = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            s1.add(i);
        }
        for (int i = 0; i < 10; i += 2) {
            s2.add(i);
        }
        for (int i = 5; i < 20; i++) {
            s3.add(i);
        }

        System.out.format("%25s: %s%n", "s1", s1);
        System.out.format("%25s: %s%n", "s2", s2);
        System.out.format("%25s: %s%n", "s3", s3);

        System.out.format("%25s: %s%n", "s1.containsAll(s2)", s1.containsAll(s2));

        Set<Integer> union = new HashSet<>(s1);
        union.addAll(s3);
        System.out.format("%25s: %s%n", "union of s1 and s2", union);

        Set<Integer> intersection = new HashSet<>(s1);
        intersection.retainAll(s3);
        System.out.format("%25s: %s%n", "intersection of s1 and s3", intersection);

        Set<Integer> difference = new HashSet<>(s1);
        difference.removeAll(s3);
        System.out.format("%25s: %s%n", "difference of s1 and s3", difference);
    }

}

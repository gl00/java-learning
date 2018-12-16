package basic.generics.exercise;

import java.util.Arrays;
import java.util.Collection;

/**
 * Write a generic method to count the number of elements input a collection that have a specific property (for example,
 * odd integers, prime numbers, palindromes).
 */
public final class Exercise1 {

    private interface UnaryPredicate<T> {
        boolean test(T t);
    }

    private static class OddPredicate implements UnaryPredicate<Integer> {
        @Override
        public boolean test(Integer integer) {
            return integer % 2 != 0;
        }
    }

    public static <T> int countIf(Collection<T> c, UnaryPredicate<T> p) {
        int count = 0;
        for (T elem : c) {
            if (p.test(elem)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Collection<Integer> li = Arrays.asList(1, 2, 3, 4);
        int count = countIf(li, new OddPredicate());
        System.out.println("Number of odd integers = " + count);
    }

}

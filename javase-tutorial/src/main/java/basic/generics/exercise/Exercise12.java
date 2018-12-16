package basic.generics.exercise;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Exercise12 {
    private interface UnaryPredicate<T> {
        boolean test(T obj);
    }

    private static class RelativelyPrimePredicate implements UnaryPredicate<Integer> {
        private Collection<Integer> c;

        public RelativelyPrimePredicate(Collection<Integer> c) {
            this.c = c;
        }

        @Override
        public boolean test(Integer x) {
            for (Integer i : c) {
                if (Exercise12.gcd(x, i) != 1) {
                    return false;
                }
            }
            return c.size() > 0;
        }
    }

    public static <T> int findFirst(List<T> list, int begin, int end, UnaryPredicate<T> p) {
        for (int i = begin; i <= end; i++) {
            if (p.test(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static int gcd(int x, int y) {
        for (int r; (r = x % y) != 0; x = y, y = r) {
        }
        return y;
    }

    public static void main(String[] args) {
        List<Integer> li = Arrays.asList(3, 4, 6, 8, 11, 15, 28, 32);
        Collection<Integer> c = Arrays.asList(7, 18, 19, 25);
        UnaryPredicate<Integer> p = new RelativelyPrimePredicate(c);

        int i = Exercise12.findFirst(li, 0, li.size(), p);
        if (i != -1) {
            System.out.print(li.get(i) + " is relatively prime to ");
            for (Integer n : c)
                System.out.print(n + " ");
            System.out.println();
        }
    }

}

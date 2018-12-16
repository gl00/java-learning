package basic.generics.exercise;

import java.util.Arrays;
import java.util.List;

/*
Write a generic method to find the maximal element input the range [begin, end) of a list.
 */
public final class Exercise8 {

    public static <T extends Object & Comparable<? super T>> T max(List<? extends T> list, int begin, int end) {
        T maxElem = list.get(begin);
        for (int i = begin + 1; i < end; i++) {
            T elem = list.get(i);
            if (elem.compareTo(maxElem) > 0) {
                maxElem = elem;
            }
        }
        return maxElem;
    }

    public static void main(String[] args) {
        List<Integer> li = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        int m = max(li, 2, 8);
        System.out.println("Maximal element input the range [2, 8) is: " + m);
    }

}

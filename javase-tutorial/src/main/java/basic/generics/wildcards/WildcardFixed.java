package basic.generics.wildcards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WildcardFixed {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        new WildcardFixed().foo(list);
    }

    void foo(List<?> i) {
        fooHelper(i);
    }

    // Helper method created so that the wildcard can be captured
    // through type inference.
    private <T> void fooHelper(List<T> l) {
        l.set(0, l.get(0));
    }

}

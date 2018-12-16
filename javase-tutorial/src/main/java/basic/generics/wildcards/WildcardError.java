package basic.generics.wildcards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WildcardError {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        new WildcardError().foo(list);
    }

    void foo(List<?> i) {
//    i.set(0, i.get(0)); // error
    }

}

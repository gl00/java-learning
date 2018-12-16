package collections.interfaces.exercises;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Take the FindDupsexample and modify it to use a SortedSet instead of a Set. Specify a Comparator so that case is
 * ignored when sorting and identifying set elements.
 */
public class Exercise2 {
    public static void main(String[] args) {
        SortedSet<String> distinctWords = new TreeSet<>(String::compareToIgnoreCase);
        distinctWords.addAll(Arrays.asList(args));
        System.out.println(distinctWords.size() + " distinct words: " + distinctWords);
    }
}

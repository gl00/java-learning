package collections.interfaces.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Anagrams {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: Anagrams <file> <size>");
            return;
        }
        int minGroupSize = Integer.parseInt(args[1]);

        // Read words from file and put into a simulated multimap
        Map<String, List<String>> m = new HashMap<>();

        Scanner s = null;
        try {
            s = new Scanner(new File(args[0]));
            while (s.hasNext()) {
                String word = s.next();
                String alpha = alphabetize(word);
                List<String> l = m.get(alpha);
                if (l == null) {
                    m.put(alpha, l = new ArrayList<String>());
                }
                l.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (s != null) {
                s.close();
            }
        }

        // Print  all permutation groups above size threshold
        for (List<String> l : m.values()) {
            if (l.size() >= minGroupSize) {
                System.out.println(l.size() + ": " + l);
            }
        }
    }

    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}

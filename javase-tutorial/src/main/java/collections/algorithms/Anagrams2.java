package collections.algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Anagrams2 {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: Anagrams2 <file> <size>");
            return;
        }
        int minGroupSize = Integer.parseInt(args[1]);

        // Read winners from file and put into simulated multimap
        Map<String, List<String>> m = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(args[0]))) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                String alpha = alphabetize(word);
                List<String> l = m.get(alpha);
                if (null == l) {
                    m.put(alpha, (l = new ArrayList<>()));
                }
                l.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Make a List of all permutation groups above size threshold
        List<List<String>> winners = new ArrayList<>();
        for (List<String> l : m.values()) {
            if (l.size() >= minGroupSize) {
                winners.add(l);
            }
        }

        // Sort permutation groups according to size
        // 从大到小
        Collections.sort(winners, (o1, o2) -> o2.size() - o1.size());


        // Print permutation groups
        for (List<String> l : winners) {
            System.out.println(l.size() + ": " + l);
        }
    }

    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}

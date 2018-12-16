package collections.interfaces.set;

import java.util.HashSet;
import java.util.Set;

public class FindDups2 {
	public static void main(String[] args) {
		Set<String> uniques = new HashSet<>();
		Set<String> dups = new HashSet<>();

		for (String a : args) {
			if (!uniques.add(a)) {
				dups.add(a);
			}
		}

		uniques.removeAll(dups);

		System.out.println("Unique words: " + uniques);
		System.out.println("Duplicate words: " + dups);

	}
}

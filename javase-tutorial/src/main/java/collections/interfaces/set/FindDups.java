package collections.interfaces.set;

import java.util.HashSet;
import java.util.Set;

public class FindDups {
	public static void main(String[] args) {
		// Set<String> distinctWords =
		// Arrays.asList(args).stream().collect(Collectors.toSet());
		Set<String> distinctWords = new HashSet<>();
		for (String arg : args) {
			distinctWords.add(arg);
		}

		System.out.println(distinctWords.size() + " distinct words: " + distinctWords);
	}
}

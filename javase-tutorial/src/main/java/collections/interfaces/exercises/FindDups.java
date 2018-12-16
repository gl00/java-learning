package collections.interfaces.exercises;

import java.util.Set;
import java.util.TreeSet;

public class FindDups {
	public static void main(String[] args) {
		Set<String> s = new TreeSet<>();
		for (String a : args)
			s.add(a);
		System.out.println(s.size() + " distinct words: " + s);
	}
}

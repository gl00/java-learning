package collections.interfaces.exercises;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Ran {
	public static void main(String[] args) {
		List<String> argList = Arrays.asList(args);
		Collections.shuffle(argList);
		
		// Print out the elements using streams
		argList.stream().forEach(e -> System.out.format("%s ", e));

		System.out.println();
		
		// Print out the elements using the traditional enhanced for statement
		for (String e : argList) {
			System.out.format("%s ", e);
		}
	}
}

package collections.interfaces.exercises;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class ListTrim {
	public static void main(String[] args) {
		List<String> l = Arrays.asList(
				" January ",
				" February ",
				" March ",
				" April ",
				" May ",
				" June ",
				" July ",
				" August ",
				" September ",
				" October ",
				" November ",
				" December ");

		System.out.println(l);
		listTrim(l);
		System.out.println(l);
	}

	public static void listTrim(List<String> l) {
		for (ListIterator<String> lit = l.listIterator(); lit.hasNext();) {
			lit.set(lit.next().trim());
		}
	}
}

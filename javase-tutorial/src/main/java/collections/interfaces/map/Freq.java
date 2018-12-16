package collections.interfaces.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Freq {
	public static void main(String[] args) {
//		Map<String, Integer> m = new HashMap<>();
//		Map<String, Integer> m = new TreeMap<>();
		Map<String, Integer> m = new LinkedHashMap<>();
		for (String a : args) {
			Integer freq = m.get(a);
			m.put(a, freq == null ? 1 : freq + 1);
		}

		System.out.println(m.size() + " distinct words:");
		System.out.println(m);
	}
}

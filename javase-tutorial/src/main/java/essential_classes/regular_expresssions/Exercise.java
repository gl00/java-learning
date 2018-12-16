package essential_classes.regular_expresssions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise {
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("([A-Z][a-zA-Z]*)\\s+\\1");
		Matcher matcher = pattern.matcher(" ");
		if (matcher.matches()) {
			System.out.println("found");
		} else {
			System.out.println("not found");
		}
	}
}

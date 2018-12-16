package basic.number_string.numbers.exercises;

public class Question {
	public static void main(String[] args) {

		// question 1.a.
		System.out.println(Integer.toHexString(65));

		// question 1.b.
		System.out.println(Integer.parseInt("230", 5));
		System.out.println(Integer.valueOf("230", 5));

		// question 1.c.
		System.out.println(Double.isNaN(1.23));

		// question 2
		System.out.println(Integer.valueOf(1).equals(Long.valueOf(1)));
	}
}

package basic.interfaces_inheritance.interfaces.default_method;

public class TestSimpleTimeClient {
	public static void main(String[] args) {
		SimpleTimeClient myTimeClient = new SimpleTimeClient();
		System.out.println("Current time: " + myTimeClient.toString());
		System.out.println("Time input California: " +
				myTimeClient.getZonedDateTime("Blah blah").toString());
	}
}

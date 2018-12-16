package essential_classes.platform_environment;

import java.util.Properties;

public class SystemPropertiesTest {
	public static void main(String[] args) {
		Properties properties = System.getProperties();
		properties.list(System.out);
	}
}
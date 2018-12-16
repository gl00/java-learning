package essential_classes.platform_environment;

import java.util.Map;

public class EnvMap {
	public static void main(String[] args) {
		String value = System.getenv("path");
		System.out.println(value);
		//		Map<String, String> env = System.getenv();
//		for (String envName : env.keySet()) {
//			System.out.format("%s = %s%n", envName, env.get(envName));
//		}
	}
}

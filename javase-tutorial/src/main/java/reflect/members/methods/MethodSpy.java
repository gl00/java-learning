package reflect.members.methods;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class MethodSpy {
    private static final String fmt = "%24s: %s%n";

    public static void main(String[] args) {
        try {
            Class<?> c = Class.forName(args[0]);
            Method[] allMethods = c.getDeclaredMethods();
            for (Method m : allMethods) {
                if (!m.getName().equals(args[1])) {
                    continue;
                }
                System.out.printf("%s%n", m.toGenericString());

                System.out.printf(fmt, "ReturnType", m.getReturnType());
                System.out.printf(fmt, "GenericReturnType", m.getGenericReturnType());

                Class<?>[] pType = m.getParameterTypes();
                Type[] gpType = m.getGenericParameterTypes();
                for (int i = 0; i < pType.length; i++) {
                    System.out.printf(fmt, "ParameterType", pType[i]);
                    System.out.printf(fmt, "GenericParameterType", gpType[i]);
                }

                Class<?>[] xType = m.getExceptionTypes();
                Type[] gxType = m.getGenericExceptionTypes();
                for (int i = 0; i < xType.length; i++) {
                    System.out.printf(fmt, "ExceptionType", xType[i]);
                    System.out.printf(fmt, "GenericExceptionType", gxType[i]);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    <E extends RuntimeException> void genericThrow() throws E {
    }
}

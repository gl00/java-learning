package reflect.members.methods;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodParameterSpy {
    private static final String fmt = "%24s: %s%n";

    public static void main(String[] args) {
        try {
            printClassConstructor(Class.forName(args[0]));
            printClassMethods(Class.forName(args[0]));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printClassConstructor(Class<?> c) {
        Constructor<?>[] allConstructors = c.getConstructors();
        System.out.printf("%nNumber of constructors: %d%n", allConstructors.length);
        for (int i = 0; i < allConstructors.length; i++) {
            System.out.printf("%nConstructor #%d%n", (i + 1));
            printConstructor(allConstructors[i]);
        }

        Constructor<?>[] allDeclConst = c.getDeclaredConstructors();
        System.out.printf("%nNumber of declared constructors: %d%n", allDeclConst.length);
        for (int i = 0; i < allDeclConst.length; i++) {
            System.out.printf("%nDeclared constructor #%d%n", (i + 1));
            printConstructor(allDeclConst[i]);
        }
    }

    public static void printClassMethods(Class c) {
        Method[] allMethods = c.getDeclaredMethods();
        System.out.printf("%nNumber of methods: %d%n", allMethods.length);
        for (int i = 0; i < allMethods.length; i++) {
            System.out.printf("%nMethod #%d%n", (i + 1));
            printMethod(allMethods[i]);
        }
    }

    public static void printMethod(Method m) {
        System.out.printf("%s%n", m.toGenericString());
        System.out.printf(fmt, "Return type", m.getReturnType());
        System.out.printf(fmt, "Generic return type", m.getGenericReturnType());

        Parameter[] params = m.getParameters();
        for (int i = 0; i < params.length; i++) {
            printParameter(params[i]);
        }
    }

    public static void printConstructor(Constructor<?> c) {
        System.out.printf("%s%n", c.toGenericString());
        Parameter[] params = c.getParameters();
        System.out.printf(fmt, "Number of parameters", params.length);
        for (int i = 0; i < params.length; i++) {
            printParameter(params[i]);
        }
    }

    public static void printParameter(Parameter p) {
        System.out.printf(fmt, "Parameter class", p.getType());
        System.out.printf(fmt, "Parameter name", p.getName());
        System.out.printf(fmt, "Modifiers", p.getModifiers());
        System.out.printf(fmt, "Is implicit?", p.isImplicit());
        System.out.printf(fmt, "Is name present?", p.isNamePresent());
        System.out.printf(fmt, "Is synthetic?", p.isSynthetic());
    }

    // for morbidly curious
    <E extends RuntimeException> void genericThrow() throws E {
    }
}

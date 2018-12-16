package reflect.members.methods;

public class MethodParameterExamples {
    public static void main(String[] args) {
        System.out.println("InnerClass:");
        MethodParameterSpy.printClassConstructor(InnerClass.class);

        System.out.println("enum Colors:");
        MethodParameterSpy.printClassConstructor(Colors.class);
        MethodParameterSpy.printClassMethods(Colors.class);
    }

    enum Colors {RED, WHITE}

    public class InnerClass {
    }
}

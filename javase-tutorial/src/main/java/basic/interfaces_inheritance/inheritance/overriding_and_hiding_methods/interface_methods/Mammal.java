package basic.interfaces_inheritance.inheritance.overriding_and_hiding_methods.interface_methods;

public interface Mammal {
    String identifyMyself();
    // 注意：接口中的静态方法永远不会被继承。
    static void aStaticMethod() {
        System.out.println("aStaticMethod input Mammal.");
    }
}

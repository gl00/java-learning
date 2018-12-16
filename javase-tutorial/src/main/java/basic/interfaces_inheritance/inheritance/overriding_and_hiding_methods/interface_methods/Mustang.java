package basic.interfaces_inheritance.inheritance.overriding_and_hiding_methods.interface_methods;

public class Mustang extends Horse implements Mammal {
    public static void main(String[] args) {
        Mustang mustang = new Mustang();
        /*
        类中的继承实例方法可以覆盖抽象接口方法。考虑以下接口和类：
        接口 Mammal 中定义了抽象方法 identifyMyself
        类 Horse 定义了实例方法 identifyMyself
        Mustang 继承类 Horse 并实现接口 Mammal，
        从 Horse 继承过来的 identifyMyself 方法重写了接口 Mammal 中的抽象同名方法 identifyMyself
         */
        System.out.println(mustang.identifyMyself());

        // 注意：接口中的静态方法永远不会被继承。
        // 所以只能通过接口名来调用它里面的静态方法。
        Mammal.aStaticMethod();
    }
}

package basic.interfaces_inheritance.inheritance.overriding_and_hiding_methods.interface_methods;

public class Pegasus extends Horse implements Flyer, Mythical {
    public static void main(String[] args) {
        Pegasus pegasus = new Pegasus();
        /*
        实例方法优于接口默认方法。
        Horse 定义了实例方法 identifyMyself
        Flyer、Mythical 定义了各自的默认方法 identifyMyself
        Pegasus 继承类 Horse 并实现接口 Flyer 和 Mythical
         */
        System.out.println(pegasus.identifyMyself());
    }
}

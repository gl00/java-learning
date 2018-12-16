package basic.interfaces_inheritance.inheritance.overriding_and_hiding_methods.interface_methods;

public class Dragon implements EggLayer, FireBreather {

    public static void main(String... args) {
        Dragon myApp = new Dragon();
        /*
        Dragon 实现了两个接口 EggLayer， FireBreather。
        这两个接口都继承了接口 Animal。
        其中 EggLayer 重写了 Animal 的默认方法 identifyMyself,
        但 FireBreather 没有重写该默认方法。
        此时，Dragon 继承过来的 identifyMyself 方法可以唯一确定，就是接口 EggLayer 中定义的那个。

        如果 FireBreather 也重写了 Animal 的默认方法 identifyMyself，
        那么 Dragon 继承过来的 identifyMyself 方法不能唯一确定，此时必须在 Dragon 中重写 identifyMyself 方法。
         */
        System.out.println(myApp.identifyMyself());
    }

//    @Override
//    public String identifyMyself() {
//        return EggLayer.super.identifyMyself() + " - "
//                + FireBreather.super.identifyMyself() + " - "
//                + "I am a dragon";
//    }

}

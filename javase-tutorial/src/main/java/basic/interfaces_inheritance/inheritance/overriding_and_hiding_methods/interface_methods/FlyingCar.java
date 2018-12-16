package basic.interfaces_inheritance.inheritance.overriding_and_hiding_methods.interface_methods;

public class FlyingCar implements OperateCar, FlyCar {
    /*
    FlyingCar 实现的接口 OperateCar 和 FlyCar 都定义了默认方法 startEngine,
    此时 FlyingCar 继承过来的 startEngine 方法不能唯一确定，必须重写！
     */
    @Override
    public void startEngine() {
        /*
        可以使用父接口名 + super 关键字调用父接口中的默认方法。
        光用 super 无法唯一确定是哪一个接口中的方法。
         */
        FlyCar.super.startEngine();
        OperateCar.super.startEngine();
        System.out.println("startEngine input FlyingCar.");
    }

    public static void main(String[] args) {
        new FlyingCar().startEngine();
    }
}

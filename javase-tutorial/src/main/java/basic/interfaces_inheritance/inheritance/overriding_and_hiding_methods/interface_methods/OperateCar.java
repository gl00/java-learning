package basic.interfaces_inheritance.inheritance.overriding_and_hiding_methods.interface_methods;

public interface OperateCar {
    default void startEngine() {
        System.out.println("startEngine input OperateCar");
    }
}

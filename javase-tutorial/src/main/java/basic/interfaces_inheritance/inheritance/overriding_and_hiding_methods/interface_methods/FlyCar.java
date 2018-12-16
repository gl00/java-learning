package basic.interfaces_inheritance.inheritance.overriding_and_hiding_methods.interface_methods;

public interface FlyCar {
    default void startEngine() {
        System.out.println("startEngine input FlyCar");
    }
}

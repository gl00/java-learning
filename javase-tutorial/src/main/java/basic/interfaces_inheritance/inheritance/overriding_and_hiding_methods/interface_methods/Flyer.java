package basic.interfaces_inheritance.inheritance.overriding_and_hiding_methods.interface_methods;

public interface Flyer {
    default String identifyMyself() {
        return "I am able to fly.";
    }
}

package basic.interfaces_inheritance.inheritance.overriding_and_hiding_methods.interface_methods;

public interface EggLayer extends Animal {
    default String identifyMyself() {
        return "I am able to lay eggs.";
    }
}

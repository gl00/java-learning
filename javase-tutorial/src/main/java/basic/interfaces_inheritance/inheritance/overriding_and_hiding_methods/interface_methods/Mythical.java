package basic.interfaces_inheritance.inheritance.overriding_and_hiding_methods.interface_methods;

public interface Mythical {
    default String identifyMyself() {
        return "I am a mythical creature.";
    }
}

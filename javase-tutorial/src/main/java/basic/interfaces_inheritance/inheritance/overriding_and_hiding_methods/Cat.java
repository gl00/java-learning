package basic.interfaces_inheritance.inheritance.overriding_and_hiding_methods;

public class Cat extends Animal {

    public static void testClassMethod() {
        System.out.println("The static method input Cat");
    }

    @Override
    public void testInstanceMethod() {
        System.out.println("The instance method input Cat");
    }

    public static void main(String[] args) {
        Cat myCat = new Cat();
        Animal myAnimal = myCat;
        Animal.testClassMethod();
        myAnimal.testInstanceMethod();
    }

}

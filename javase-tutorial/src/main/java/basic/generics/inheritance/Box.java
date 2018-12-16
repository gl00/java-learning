package basic.generics.inheritance;

public class Box<T extends Number> {
  private T t;

  public Box(T t) {
    this.t = t;
  }

  public static void someMethod(Number number) {
    System.out.println(number.doubleValue());
  }

  public static void boxTest(Box<Number> numberBox) {
    // do something
  }

  public static void main(String[] args) {

    Object someObject = new Object();
    Integer someInteger = Integer.valueOf(10);
    // Object 是 Integer 的父类
    // Integer is a Object, "is a"关系
    // Java 中可以把子类对象赋值给父类引用，即父类引用可以指向子类对象。这就是Java中的多态。
    someObject = someInteger;  // OK

    // 同理。调用方法时传递的实参也可以是形参的子类对象
    someMethod(Integer.valueOf(10)); // OK
    someMethod(Double.valueOf(10.12)); // OK


    /*
    形参类型是 Box<Number>
    传递一个 Box<Integer>
    但 Box<Integer> 和 Box<Number> 之间没有继承关系！

    给定两个具体类型A和B（例如Number和Integer），MyClass <MALE>与MyClass <FEMALE>没有关系，
    不管A和B是否相关。 MyClass <MALE>和MyClass <FEMALE>的公共父项是Object。
    */
//    boxTest(new Box<Integer>(10)); // ERROR
  }

  public T getT() {
    return t;
  }

  public void setT(T t) {
    this.t = t;
  }
}

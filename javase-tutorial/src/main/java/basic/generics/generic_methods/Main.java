package basic.generics.generic_methods;

public class Main {
  public static void main(String[] args) {
    Pair<Integer, String> p1 = new Pair<>(1, "apple");
    Pair<Integer, String> p2 = new Pair<>(2, "pear");
    // 调用泛型方法，显式指定泛型参数类型
//    boolean same = Util.<Integer, String>compare(p1, p2);
    // 实际上可以像调用普通方法一样调用泛型方法，因为编译器可以推断出类型参数
    boolean same = Util.compare(p1, p2);
    System.out.println(same);
  }
}

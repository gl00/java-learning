package basic.generics.bounded_type_parameters;

public class Box<T> {
  private T t;

  public static void main(String[] args) {
    Box<Integer> integerBox = new Box<>();
    integerBox.setT(10);
    integerBox.inspect(1.23);
    //    integerBox.inspect("some text"); // error
  }

  public T getT() {
    return t;
  }

  public void setT(T t) {
    this.t = t;
  }

  // 使用有界类型参数限制可用的类型参数
  public <U extends Number> void inspect(U u) {
    System.out.println("WalkFileTree: " + t.getClass().getName());
    System.out.println("U: " + u.getClass().getName());
  }
}

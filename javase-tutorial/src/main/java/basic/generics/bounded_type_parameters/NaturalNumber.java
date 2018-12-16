package basic.generics.bounded_type_parameters;

public class NaturalNumber<T extends Integer> {
  private T n;

  public NaturalNumber(T n) {
    this.n = n;
  }

  public boolean isEven() {
    // 调用边界Integer中定义的方法
    return n.intValue() % 2 == 0;
  }

}

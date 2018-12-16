package basic.generics.type_erasure;

public class NodeClient {
  public static void main(String[] args) {
    MyNode mn = new MyNode(5);
    Node n = mn;
    n.setData("Hello"); // java.lang.ClassCastException: java.base/java.lang.String cannot be cast to java.base/java.lang.Integer
    Integer x = mn.data;
  }
}

package basic.generics.type_erasure;

public class MyNode extends Node<Integer> {

  public MyNode(Integer data) {
    super(data);
  }

  @Override
  public void setData(Integer data) {
    System.out.println("MyNode.setData");
    super.setData(data);
  }
}

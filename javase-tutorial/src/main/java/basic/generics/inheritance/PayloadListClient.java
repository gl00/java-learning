package basic.generics.inheritance;

import java.util.List;

public class PayloadListClient {
  public static void main(String[] args) {
    // 泛型类和子类化
    // The following parameterizations of PayloadList are subtypes of List<String>:
    List<String> list = new PayloadListImpl<String, String>();
    List<String> list2 = new PayloadListImpl<String, Integer>();
    List<String> list3 = new PayloadListImpl<String, Exception>();

    // 错误。类型不符合定义: PayloadList<E, P> extends List<E>
//    List<String> list4 = new PayloadListImpl<Integer, String>(); // ERROR
  }
}

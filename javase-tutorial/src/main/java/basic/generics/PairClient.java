package basic.generics;

public class PairClient {
	public static void main(String[] args) {
		Pair<String, Integer> p1 = new OrderedPair<String, Integer>("Even", 8);
		Pair<String, String> p2 = new OrderedPair<String, String>("hello", "world");

		// 从Java SE 7开始，可以省略掉类型实参，只用一对尖括号，只要编译器能从上下文确定或推断出类型实参
		Pair<String, Integer> p3 = new OrderedPair<>("One", 1);
		Pair<String, String> p4 = new OrderedPair<>("good", "bye");
	}
}

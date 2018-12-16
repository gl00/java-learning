package basic.generics.generic_types;

public class Box<T> {

	private T t;

	public T get() {
		return t;
	}

	public void set(T t) {
		this.t = t;
	}

	public static void main(String[] args) {
		// Box<Integer> intBox = new Box<>();
		// Box rawBox = new Box(); // Box是Box<WalkFileTree>的原始类型
		//
		// Box<String> stringBox = new Box<>();
		// Box rawBox2 = stringBox; // 为了向后兼容，允许分配参数化类型给它的原始类型

		// 将原始类型分配给参数化类型，则会收到警告
		// Box rawBox = new Box(); // rawBox is a raw type of Box<WalkFileTree>
		// Box<Integer> intBox = rawBox; // warning: unchecked conversion

		// 使用原始类型调用相应泛型类型上的泛型方法也会收到警告
		Box<String> stringBox = new Box<>();
		Box rawBox = stringBox;
		rawBox.set(8);
	}

}

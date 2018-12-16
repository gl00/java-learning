package basic.generics.type_inference;

import java.util.ArrayList;
import java.util.List;

public class BoxDemo {

    private static class Box<T> {

        private T t;

        public T get() {
            return t;
        }

        public void set(T t) {
            this.t = t;
        }

    }

    public static <U> void addBox(U u, List<Box<U>> boxes) {
        Box<U> box = new Box<>();
        box.set(u);
        boxes.add(box);
    }

    public static <U> void outputBoxes(List<Box<U>> boxes) {
        int counter = 0;
        for (Box<U> box : boxes) {
            U boxContents = box.get();
            System.out.println("Box #" + counter + " contains [" + boxContents.toString() + "]");
            counter++;
        }
    }

    public static void main(String[] args) {
        List<Box<Integer>> listOfIntegerBoxes = new ArrayList<>();

        // 调用泛型方法时可以使用类型见证（type witness）来指定类型参数
        // 注：这里的类型见证就是 <Integer>
        BoxDemo.addBox(10, listOfIntegerBoxes);

        // 或者，如果省略类型见证，Java编译器会自动推断（从方法的参数）类型参数是Integer
        BoxDemo.addBox(20, listOfIntegerBoxes);
        BoxDemo.addBox(30, listOfIntegerBoxes);

        BoxDemo.outputBoxes(listOfIntegerBoxes);
    }

}

package basic.class_object.nested_class.static_nested_class;

public class OuterClass {
    /*
    与类方法和变量一样，静态嵌套类与其外部类相关联。和静态类方法一样，
    静态嵌套类不能直接引用其封闭类中定义的实例变量或方法：它只能通过对象引用来使用它们。

    注意：静态嵌套类与其外部类（和其他类）的实例成员交互，就像任何其他顶级类一样。
    实际上，静态嵌套类在行为上是一个顶级类，它已嵌套在另一个顶级类中以方便打包。
     */
    static class StaticNestedClass {
        private int x = 1;

        public StaticNestedClass(int x) {
            this.x = x;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }

    public static void main(String[] args) {
        // 创建一个静态嵌套类的对象
        StaticNestedClass nestedObject = new StaticNestedClass(10);
        System.out.println(nestedObject.getX());
    }
}

package basic.class_object.nested_class.inner_class;

public class OuterClass {
    /*
与实例方法和变量一样，内部类与其封闭类的实例相关联，并且可以直接访问该对象的方法和字段。
此外，由于内部类与实例相关联，因此它本身无法定义任何静态成员。

作为内部类的实例的对象存在于外部类的实例中。考虑以下类：

class OuterClass {
    ...
    class InnerClass {
        ...
    }
}

InnerClass的实例只能存在于OuterClass的实例中，并且可以直接访问其封闭实例的方法和字段。

要实例化内部类，必须首先实例化外部类。然后，使用以下语法在外部对象中创建内部对象：

OuterClass.InnerClass innerObject = outerObject.new InnerClass();

有两种特殊的内部类：local classes (本地类) 和 anonymous classes（匿名类）。

     */
    class InnerClass {
        private String s = "Hello from InnerClass";

        public String getS() {
            return s;
        }
    }

    public static void main(String[] args) {
        OuterClass outerObject = new OuterClass();
        InnerClass innerObject = outerObject.new InnerClass();
        System.out.println(innerObject.getS());
    }
}

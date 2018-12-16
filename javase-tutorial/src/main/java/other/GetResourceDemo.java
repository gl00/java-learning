package other;

/*
引用自网络博客：

java获取当前类的绝对路径

1.如何获得当前文件路径
常用：
(1).Test.class.getResource("")
得到的是当前类FileTest.class文件的URI目录。不包括自己！

(2).Test.class.getResource("/")
得到的是当前的classpath的绝对URI路径。

(3).Thread.currentThread().getContextClassLoader().getResource("")
得到的也是当前ClassPath的绝对URI路径。

(4).Test.class.getClassLoader().getResource("")
得到的也是当前ClassPath的绝对URI路径。

(5).ClassLoader.getSystemResource("")
得到的也是当前ClassPath的绝对URI路径。
尽量不要使用相对于System.getProperty("user.dir")当前用户目录的相对路径，后面可以看出得出结果五花八门。

(6) new File("").getAbsolutePath()也可用。

// https://stackoverflow.com/questions/6608795/what-is-the-difference-between-class-getresource-and-classloader-getresource#answer-6608848
Class.getResource可以采用“相对”资源名称，该名称相对于类的包进行处理。或者，您可以使用前导斜杠指定“绝对”资源名称。类加载器资源路径始终被视为绝对路径。
Class.getResource can take a "relative" resource name, which is treated relative to the class's package. Alternatively you can specify an "absolute" resource name by using a leading slash. Classloader resource paths are always deemed to be absolute.

So the following are basically equivalent:

foo.bar.Baz.class.getResource("xyz.txt");
foo.bar.Baz.class.getClassLoader().getResource("foo/bar/xyz.txt");
And so are these (but they're different from the above):

foo.bar.Baz.class.getResource("/data/xyz.txt");
foo.bar.Baz.class.getClassLoader().getResource("data/xyz.txt");

*/
public class GetResourceDemo {
    public static void main(String[] args) {

        // 返回类所在绝对路径，包括包名
        System.out.println(GetResourceDemo.class.getResource(""));
        System.out.println(GetResourceDemo.class.getResource("").getPath());
        System.out.println();

        // 返回类所在项目的绝对路径，不带包名
        System.out.println(GetResourceDemo.class.getResource("/"));
        System.out.println(GetResourceDemo.class.getResource("/").getPath());
        System.out.println();

        // 不知道类名，可通过实例.getClass() 获取
        GetResourceDemo demo = new GetResourceDemo();
        System.out.println(demo.getClass().getResource(""));
        System.out.println(demo.getClass().getResource("").getPath());
        System.out.println();

        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        System.out.println();

        System.out.println(GetResourceDemo.class.getClassLoader().getResource(""));
        System.out.println(GetResourceDemo.class.getClassLoader().getResource("").getPath());
        System.out.println();

//        System.out.println(GetResourceDemo.class.getClassLoader().getResource("/"));            // null
//        System.out.println(GetResourceDemo.class.getClassLoader().getResource("/").getPath());  // NPE

        // 不知道类名，又在 static 块内不能使用 this 获取类的情况下，可以使用 Object 类
//        System.out.println(Object.class.getResource(""));           // null
//        System.out.println(Object.class.getResource("").getPath()); // NPE (空指针异常）
//        System.out.println(Object.class.getResource("/"));           // null
//        System.out.println(Object.class.getResource("/").getPath()); // NPE (空指针异常）

        System.out.println(GetResourceDemo.class.getProtectionDomain());
        System.out.println(GetResourceDemo.class.getProtectionDomain().getCodeSource());
        System.out.println(GetResourceDemo.class.getProtectionDomain().getCodeSource().getLocation());
        System.out.println(GetResourceDemo.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println(GetResourceDemo.class.getProtectionDomain().getCodeSource().getLocation().getFile());

    }
}

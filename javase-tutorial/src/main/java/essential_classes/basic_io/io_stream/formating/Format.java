package essential_classes.basic_io.io_stream.formating;

public class Format {
    public static void main(String[] args) {
        int i = 2;
        double r = Math.sqrt(i);
        System.out.format("The square root of %d is %f.%n", i, r);

        System.out.format("%f, %1$+020.10f%n", Math.PI);
        /* 其中 1$ 是参数索引。指的是格式说明符后的第几个参数，从 1 开始计算。
        参数索引允许您显式匹配指定的参数。您还可以指定 < 以匹配与前一个说明符相同的参数。
        因此，该示例也可以这样写：System.out.format("%f, %<+020.10f%n", Math.PI);
         */
        System.out.format("%f, %<+020.10f%n", Math.PI);
    }
}

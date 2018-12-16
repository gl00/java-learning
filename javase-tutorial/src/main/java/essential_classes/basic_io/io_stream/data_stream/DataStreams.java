package essential_classes.basic_io.io_stream.data_stream;

import java.io.*;

public class DataStreams {
    static final String dataFile = "test/out/invoicedata";

    static final double[] prices = {19.99, 9.99, 15.99, 3.99, 4.99};
    static final int[] units = {12, 8, 13, 29, 50};
    static final String[] descs = {
            "Java WalkFileTree-shirt",
            "Java Mug",
            "Duke Juggling Dolls",
            "Java Pin",
            "Java Key Chain"};

    public static void main(String[] args) throws IOException {
        DataOutputStream out = null;

        try {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)));

            for (int i = 0; i < prices.length; i++) {
                out.writeDouble(prices[i]);
                out.writeInt(units[i]);
                out.writeUTF(descs[i]);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }

        DataInputStream in = null;
        double total = 0.0;
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)));

            double price;
            int unit;
            String desc;

            try {
                while (true) {
                    /*
                    另请注意，DataStream中的每个专用写入都与相应的专用读取完全匹配。
                    程序员必须确保输出类型和输入类型以这种方式匹配：输入流由简单的二进制数据组成，没有任何信息可以知道每个值的类型及其在流中的开始位置。

                    读取和写入的类型和顺序都要匹配。
                    本例中写入的顺序是 writeDouble, writeInt, writeUTF
                    那读取的顺序也该是 readDouble, readInt, readUTF
                    类型或顺序不匹配都会导致错误。
                     */
                    price = in.readDouble();
                    unit = in.readInt();
                    desc = in.readUTF();
                    System.out.format("You ordered %d units of %s at $%.2f%n",
                            unit, desc, price);
                    total += unit * price;
                }
            } catch (EOFException e) {
                /*
                请注意，DataStreams通过捕获EOFException来检测文件结束条件，而不是测试无效的返回值。
                 DataInput方法的所有实现都使用EOFException而不是返回值。
                 */
                System.out.println("End of file.");
            }
            System.out.format("For a TOTAL of: $%.2f%n", total);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
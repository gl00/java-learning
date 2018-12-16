package essential_classes.basic_io.io_stream.object_stream;

import java.io.*;
import java.math.BigDecimal;
import java.util.Calendar;

public class ObjectStreams {

    private static final String dataFile = "test/out/invoicedata";

    private static final BigDecimal[] prices = {
            new BigDecimal("19.99"),
            new BigDecimal("9.99"),
            new BigDecimal("15.99"),
            new BigDecimal("3.99"),
            new BigDecimal("4.99")};
    private static final int[] units = {12, 8, 13, 29, 50};
    private static final String[] descs = {
            "Java WalkFileTree-shirt",
            "Java Mug",
            "Duke Juggling Dolls",
            "Java Pin",
            "Java Key Chain"};

    public static void main(String[] args) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)))) {
            out.writeObject(Calendar.getInstance());
            for (int i = 0; i < prices.length; i++) {
                out.writeObject(prices[i]);
                out.writeInt(units[i]);
                out.writeUTF(descs[i]);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + dataFile);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: ");
            e.printStackTrace();
        }

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dataFile)))) {
            Calendar date;
            BigDecimal price;
            int unit;
            String desc;
            BigDecimal total = new BigDecimal(0);

            date = (Calendar) in.readObject();
            System.out.format("On %tA, %<tB %<te, %<tY:%n", date);
            try {
                while (true) {
                    price = (BigDecimal) in.readObject();
                    unit = in.readInt();
                    desc = in.readUTF();
                    System.out.format("You ordered %d units of %s at $%.2f%n", unit, desc, price);
                    total = total.add(price.multiply(new BigDecimal(unit)));
                }
            } catch (EOFException e) {
                // end of file
            }
            System.out.format("For a TOTAL of: $%.2f%n", total);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

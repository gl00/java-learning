package reflect.members.fields;

import java.lang.reflect.Field;
import java.util.Arrays;

enum Tweedle {DEE, DUM}

/**
 * Getting and Setting Field Values demo
 */
public class Book {
    public long chapters = 0;
    protected String[] characters = {"Alice", "White Rabbit"};
    Tweedle twin = Tweedle.DEE;
    private double price = 99.99;

    public static void main(String[] args) {
        Book book = new Book();
        String fmt = "%6sS:  %-12s = %s%n";

        Class<? extends Book> c = book.getClass();

        try {
            Field chap = c.getDeclaredField("chapters");
            System.out.printf(fmt, "before", "chapters", book.chapters);
            chap.setLong(book, 12);
            System.out.printf(fmt, "after", "chapter", chap.getLong(book));

            Field chars = c.getDeclaredField("characters");
            System.out.printf(fmt, "before", "characters", Arrays.asList(book.characters));
            String[] newChars = {"Queen", "King"};
            chars.set(book, newChars);
            System.out.printf(fmt, "after", "characters", Arrays.asList(book.characters));

            Field t = c.getDeclaredField("twin");
            System.out.printf(fmt, "before", "twin", book.twin);
            t.set(book, Tweedle.DUM);
            System.out.printf(fmt, "after", "twin", t.get(book));

            Field p = c.getDeclaredField("price");
            System.out.printf(fmt, "before", "price", book.price);
            p.setDouble(book, 100);
            System.out.printf(fmt, "after", "price", p.get(book));

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

package datetime.exercises;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

// Exercise 3
public class Superstitious {
  public static void main(String[] args) {
    Month month = null;
    LocalDate date = null;

    if (args.length < 2) {
      System.out.printf("Usage: Superstitious <month> <day>%n");
      throw new IllegalArgumentException();
    }

    try {
      month = Month.valueOf(args[0].toUpperCase());
    } catch (IllegalArgumentException e) {
      System.out.printf("%s is not a valid month.%n", month);
      throw e;
    }

    int day = Integer.parseInt(args[1]);

    try {
      date = Year.now().atMonth(month).atDay(day);
    } catch (DateTimeException e) {
      System.out.printf("%s %s is not a valid date.%n", month, day);
      throw e;
    }

    //    boolean b = date.getDayOfWeek() == DayOfWeek.FRIDAY && date.getDayOfMonth() == 13;
    //    System.out.println(b);
    System.out.println(date.query(new FridayThirteenQuery()));
  }
}

package datetime.exercises;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;

// Exercise 2
public class ListMondays {
  public static void main(String[] args) {
    Month month = null;

    if (args.length < 1) {
      System.out.println("Usage: ListMonths <month>");
      throw new IllegalArgumentException();
    }

    try {
      month = Month.valueOf(args[0].toUpperCase());
    } catch (IllegalArgumentException e) {
      System.out.printf("%s is not a valid month.%n", args[0]);
      throw e;
    }

    System.out.printf("For the month of %s:%n", month);
    LocalDate date =
        Year.now().atMonth(month).atDay(1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    Month m = date.getMonth();
    while (m == month) {
      System.out.println(date);
      date = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
      m = date.getMonth();
    }
  }
}

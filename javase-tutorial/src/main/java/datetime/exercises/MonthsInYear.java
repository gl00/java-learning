package datetime.exercises;

import java.time.DateTimeException;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

// Exercise 1
public class MonthsInYear {
  public static void main(String[] args) {
    int year = 0;

    if (args.length <= 0) {
      System.out.printf("Usage: MonthsInYear <year>%n");
      throw new IllegalArgumentException();
    }

    try {
      year = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      System.out.printf("%s is not a properly formatted number.%n", args[0]);
      throw e;
    }

    try {
      Year test = Year.of(year);
    } catch (DateTimeException e) {
      System.out.printf("%d is not valid year.%n", year);
      throw e;
    }

    System.out.println("For the year " + year);
    for (Month month : Month.values()) {
      YearMonth ym = YearMonth.of(year, month);
      System.out.printf("%s: %d days%n", month, ym.lengthOfMonth());
    }
  }
}

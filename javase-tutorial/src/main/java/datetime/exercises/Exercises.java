package datetime.exercises;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

public class Exercises {
    public static void main(String[] args) {
//        exercise1();
//        exercise2();
        exercise3();
    }

    /*
    1. Write an example that, for a given year, reports the length of each month within that year.
     */
    private static void exercise1() {
        int year = 2011;
        for (Month m : Month.values()) {
            YearMonth yearMonth = YearMonth.of(year, m);
            System.out.println(yearMonth.lengthOfMonth());

        }
    }

    /*
    2. Write an example that, for a given month of the current year, lists all of the Mondays input that month.
     */
    private static void exercise2() {
        int month = 11;
        LocalDate date = LocalDate.now().withMonth(month).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        for (; date.getMonthValue() == month; ) {
            System.out.println(date);
            date = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
    }

    /*
    3. Write an example that tests whether a given date occurs on Friday the 13th.
     */
    private static void exercise3() {
        LocalDate date = LocalDate.now();

        if (date.getDayOfWeek() == DayOfWeek.FRIDAY && date.getDayOfMonth() == 13) {
            System.out.println("Friday 13th");
        } else {
            System.out.println("no");
        }
    }
}

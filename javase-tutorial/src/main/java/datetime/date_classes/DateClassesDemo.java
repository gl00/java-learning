package datetime.date_classes;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

public class DateClassesDemo {
    public static void main(String[] args) {
        // LocalDate
        LocalDate date = LocalDate.now();
        LocalDate nextSat = date.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

        System.out.println(date);
        System.out.println(nextSat);

        // YearMonth
        YearMonth yearMonth = YearMonth.now();
        System.out.println(yearMonth.lengthOfYear());

        // MonthDay
        MonthDay monthDay = MonthDay.of(Month.FEBRUARY, 29);
        boolean validLeapYear = monthDay.isValidYear(Year.now().getValue());
        System.out.println(validLeapYear);
        System.out.println(Year.now().getValue());

        // Year
        boolean isLeapYear = Year.of(2012).isLeap();
        System.out.println(isLeapYear);
    }
}

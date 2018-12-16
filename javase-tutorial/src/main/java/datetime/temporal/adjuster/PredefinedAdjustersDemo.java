package datetime.temporal.adjuster;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class PredefinedAdjustersDemo {
    public static void main(String[] args) {
        // Temporal Adjuster
        // Predefined Adjusters
        LocalDate now = LocalDate.now();
        DayOfWeek dow = now.getDayOfWeek();
        System.out.printf("%s is on a %s%n", now, dow);

        System.out.printf("first day of Month: %s%n", now.with(TemporalAdjusters.firstDayOfMonth()));
        System.out.printf("first Monday of Month: %s%n", now.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)));
        System.out.printf("last day of Month: %s%n", now.with(TemporalAdjusters.lastDayOfMonth()));
        System.out.printf("first day of next Month: %s%n", now.with(TemporalAdjusters.firstDayOfNextMonth()));
        System.out.printf("first day of next Year: %s%n", now.with(TemporalAdjusters.firstDayOfNextYear()));
        System.out.printf("first day of Year: %s%n", now.with(TemporalAdjusters.firstDayOfYear()));

    }
}

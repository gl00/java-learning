package datetime.period_duration;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class PeriodDemo {
    public static void main(String[] args) {
        // Period
        LocalDate birthday = LocalDate.of(1988, 3, 1);
        LocalDate today = LocalDate.now();
        Period p = Period.between(birthday, today);
        long p2 = ChronoUnit.DAYS.between(birthday, today);
        System.out.printf("Your are %d years, %d months, and %d days old. ( %d days total)%n",
                p.getYears(), p.getMonths(), p.getDays(), p2);

    }
}

package datetime.period_duration;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Birthday {
  public static void main(String[] args) {
    LocalDate birthday = LocalDate.of(1988, 3, 1);
    LocalDate today = LocalDate.now();
    LocalDate nextBirthday = birthday.withYear(today.getYear());

    // If your birthday has occurred this year already, addBox 1 to the year.
    if (nextBirthday.isBefore(today) || nextBirthday.isEqual(today)) {
      nextBirthday = nextBirthday.plusYears(1);
    }

    Period p = Period.between(today, nextBirthday);
    long p2 = ChronoUnit.DAYS.between(today, nextBirthday);
    System.out.printf(
        "There are %d months, and %d days until your next birthday. (%d total)",
        p.getMonths(), p.getDays(), p2);
  }
}

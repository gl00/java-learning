package datetime.temporal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;

public class FieldAndUnitDemo {
    public static void main(String[] args) {
        // ChronoField and IsoFields
        System.out.println("Is LocalDate supports ChronoField.CLOCK_HOUR_OF_DAY?: " +
                LocalDate.now().isSupported(ChronoField.CLOCK_HOUR_OF_DAY));
        int i = LocalTime.now().get(ChronoField.MINUTE_OF_HOUR);
        System.out.println(i);
        int quarterOfYear = LocalDate.now().get(IsoFields.QUARTER_OF_YEAR);
        System.out.println(quarterOfYear);

        // ChronoUnit
        System.out.println("Is LocalDate supports ChronoUnit.DAYS?: " +
                LocalDateTime.now().isSupported(ChronoUnit.DAYS));
    }
}


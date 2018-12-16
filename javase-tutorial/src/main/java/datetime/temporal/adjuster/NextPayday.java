package datetime.temporal.adjuster;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class NextPayday {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: NextPayday <month> <day>");
            throw new IllegalArgumentException();
        }
        Month month;
        try {
            month = Month.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println(args[0] + " is not a valid month.");
            throw e;
        }

        LocalDate date;
        int day = Integer.parseInt(args[1]);
        try {
            date = Year.now().atMonth(month).atDay(day);
        } catch (DateTimeException e) {
            System.err.println(month.toString() + day + " is not a valid date.");
            throw e;
        }

        LocalDate nextPayday = date.with(new PaydayAdjuster());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM d").withLocale(Locale.ENGLISH);
        System.out.format("%30s: %s%n", "Given the date", date.format(formatter));
        System.out.format("%30s: %s%n", "the next payday", nextPayday.format(formatter));
    }
}

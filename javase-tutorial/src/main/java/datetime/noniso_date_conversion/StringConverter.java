package datetime.noniso_date_conversion;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class StringConverter {
    /**
     * Converts a LocalDate (ISO) value to a ChronoLocalDate date using the provided Chronology, and then formats the
     * ChronoLocalDate to a String using a DateTimeFormatter with a SHORT pattern based on the Chronology and the
     * current Locale.
     *
     * @param localDate - the ISO date to convert and format.
     * @param chrono    - an optional Chronology. If null, then IsoChronology is used.
     */
    public static String toString(LocalDate localDate, Chronology chrono) {
        if (localDate == null) {
            return "";
        }

        if (chrono == null) {
            chrono = IsoChronology.INSTANCE;
        }

        ChronoLocalDate cDate;
        try {
            cDate = chrono.date(localDate);
        } catch (DateTimeException e) {
            System.err.println(e);
            chrono = IsoChronology.INSTANCE;
            cDate = localDate;
        }

        Locale locale = Locale.getDefault(Locale.Category.FORMAT);
        DateTimeFormatter df =
//        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                        .withLocale(locale)
                        .withChronology(chrono)
                        .withDecimalStyle(DecimalStyle.of(locale));

        return df.format(cDate);
    }

    /**
     * Parses a String to a ChronoLocalDate using a DateTimeFormatter with a short pattern based on the current Locale
     * and the provided Chronology, then converts this to a LocalDate (ISO) value.
     *
     * @param text   - the input date text input the SHORT format expected for the Chronology and the current Locale.
     * @param chrono - an optional Chronology. If null, then IsoChronology is used.
     */
    public static LocalDate fromString(String text, Chronology chrono) {
        if (text != null && !text.isEmpty()) {
            if (chrono == null) {
                chrono = IsoChronology.INSTANCE;
            }
            String pattern = "M/d/yyyy GGGGG";
            Locale locale = Locale.getDefault(Locale.Category.FORMAT);
            DateTimeFormatter df =
                    new DateTimeFormatterBuilder()
                            .parseLenient()
                            .appendPattern(pattern)
                            .toFormatter()
                            .withChronology(chrono)
                            .withDecimalStyle(DecimalStyle.of(locale));
            TemporalAccessor temporal = df.parse(text);
            ChronoLocalDate cDate = chrono.date(temporal);
            return LocalDate.from(cDate);
        }
        return null;
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        System.out.println(toString(date, null));
        System.out.println(toString(date, JapaneseChronology.INSTANCE));
        System.out.println(toString(date, MinguoChronology.INSTANCE));
        System.out.println(toString(date, ThaiBuddhistChronology.INSTANCE));
        System.out.println(toString(date, HijrahChronology.INSTANCE));

        System.out.printf(
                "%s%n", StringConverter.fromString("10/29/0008 H", JapaneseChronology.INSTANCE));
        System.out.printf(
                "%s%n", StringConverter.fromString("10/29/0085 1", MinguoChronology.INSTANCE));
        System.out.printf(
                "%s%n", StringConverter.fromString("10/29/2539 佛历", ThaiBuddhistChronology.INSTANCE));
        System.out.printf("%s%n", StringConverter.fromString("6/16/1417 1", HijrahChronology.INSTANCE));
    }
}

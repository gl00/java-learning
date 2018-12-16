package datetime.timezone_offset_classes;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Flight {
    public static void main(String[] args) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a");
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy k:mm").withLocale(Locale.ENGLISH);

        LocalDateTime leaving = LocalDateTime.of(2013, Month.JULY, 20, 19, 30);
        ZoneId leavingZone = ZoneId.of("America/Los_Angeles");
        ZonedDateTime departure = ZonedDateTime.of(leaving, leavingZone);

        try {
            String out1 = departure.format(format);
            System.out.printf("LEAVING:  %s (%s %s)%n", out1, leavingZone, departure.getOffset());
        } catch (DateTimeException e) {
            System.err.printf("%s can't be formatted!%n", departure);
            throw e;
        }

        ZoneId arrivingZone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime arrival = departure.withZoneSameInstant(arrivingZone).plusMinutes(650);

//        ZonedDateTime arrival = departure.withZoneSameInstant(arrivingZone); // 同一时间点在不同时区是什么时间

        try {
            String out2 = arrival.format(format);
            System.out.printf("ARRIVING: %s (%s %s)%n", out2, arrivingZone, arrival.getOffset());
        } catch (Exception e) {
            System.err.printf("%s can't be formatted!%n", arrival);
            throw e;
        }


        if (arrivingZone.getRules().isDaylightSavings(arrival.toInstant())) {
            System.out.printf("  (%s daylight saving time will be input effect.)%n", arrivingZone);
        } else {
            System.out.printf("  (%s standard time will be input effect.)%n", arrivingZone);
        }
    }
}

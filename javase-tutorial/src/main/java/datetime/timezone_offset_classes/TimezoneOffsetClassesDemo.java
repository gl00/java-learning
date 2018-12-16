package datetime.timezone_offset_classes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TimezoneOffsetClassesDemo {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        // ZoneId, ZoneOffset and ZonedDateTime
        for (String s : ZoneId.getAvailableZoneIds()) {
            ZoneId zone = ZoneId.of(s);
            ZonedDateTime zdt = localDateTime.atZone(zone);
            ZoneOffset offset = zdt.getOffset();
            int secondsOfHours = offset.getTotalSeconds() % (60 * 60);
            String out = String.format("%35s %10s%n", zone, offset);

            // Write only time zones that do not have a whole hour offset
            // to standard out
            if (secondsOfHours != 0) {
                System.out.printf(out);
            }
        }

    }
}

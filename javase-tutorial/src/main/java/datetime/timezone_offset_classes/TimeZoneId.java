package datetime.timezone_offset_classes;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TimeZoneId {
    public static void main(String[] args) {
        // Get the set of all time zone IDs.
        Set<String> allZones = ZoneId.getAvailableZoneIds();

        // Create a list using the set of zones and sort it.
        List<String> zoneList = new ArrayList<>(allZones);
        Collections.sort(zoneList);

        LocalDateTime dt = LocalDateTime.now();

        Path p = Paths.get("test/out/timeZones");
        try (
                BufferedWriter bw = Files.newBufferedWriter(p,
                        StandardCharsets.US_ASCII)
//                BufferedWriter bw = Files.newBufferedWriter(p,
//                StandardCharsets.US_ASCII,
                // newBufferedWriter 方法默认使用的就是
                // standardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
                //            StandardOpenOption.WRITE
//                CREATE, WRITE, TRUNCATE_EXISTING)
        ) {
            for (String s : zoneList) {
                ZoneId zone = ZoneId.of(s);
                ZonedDateTime zdt = dt.atZone(zone);
                ZoneOffset offset = zdt.getOffset();
                int secondsOfHour = offset.getTotalSeconds() % (60 * 60);
                String out = String.format("%35s %10s%n", zone, offset);

                // Write only time zones that do not have a whole hour offset to standard out.
                if (secondsOfHour != 0) {
                    System.out.printf(out);
                }

                // Write all time zones to the file.
                bw.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

package datetime.period_duration;

import java.time.Duration;
import java.time.Instant;

public class DurationDemo {
    public static void main(String[] args) {
        // Duration
        Instant epoch = Instant.EPOCH;
        Instant now = Instant.now();
        long hoursFromEpoch = Duration.between(epoch, now).toHours();
        System.out.format("从 %s 到 %s 共 %d 小时%n", epoch, now, hoursFromEpoch);
    }
}

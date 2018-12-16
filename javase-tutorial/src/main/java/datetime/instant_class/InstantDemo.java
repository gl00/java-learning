package datetime.instant_class;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class InstantDemo {
    public static void main(String[] args) {
        // Instant 表示时间轴上的时间点
        // Instant 没有包含关于时区的信息。
        // 返回的时间都是 Greenwich/UTC 时间
        Instant oneHourLater = Instant.now().plus(1, ChronoUnit.HOURS);
        System.out.println(oneHourLater);

        Instant epoch = Instant.EPOCH;
        Instant now = Instant.now();
        long secondsFromEpoch = Instant.ofEpochSecond(0L).until(now, ChronoUnit.SECONDS);
        System.out.format("从 %s 到 %s 共有 %d 秒%n", epoch, now, secondsFromEpoch);

        // Instant 转换为时间需要指定时区
        Instant timestamp = Instant.now();
        System.out.println(timestamp);  // UTC 时间
        LocalDateTime ldt = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault()); // 默认时区时间
        System.out.println(ldt);
        ZonedDateTime zdt = ZonedDateTime.ofInstant(timestamp, ZoneId.systemDefault());
        System.out.println(zdt); // 会打印时区相关信息
    }
}

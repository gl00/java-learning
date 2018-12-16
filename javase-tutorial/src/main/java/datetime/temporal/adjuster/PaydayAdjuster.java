package datetime.temporal.adjuster;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/*
 * 您还可以创建自己的自定义调整器。为此，您使用adjustInto（Temporal）方法创建一个实现TemporalAdjuster接口的类。
 * NextPayday示例中的PaydayAdjuster类是自定义调整器。 PaydayAdjuster评估传入日期并返回下一个发薪日，
 * 假设发薪日每月发生两次：在15日，再次在该月的最后一天。如果计算的日期发生在周末，则使用上一个星期五。假设当前日历年。
 */
public class PaydayAdjuster implements TemporalAdjuster {
    @Override
    public Temporal adjustInto(Temporal input) {
        LocalDate date = LocalDate.from(input);
        int day;
        if (date.getDayOfMonth() < 15) {
            day = 15;
        } else {
            day = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
        }
        date = date.withDayOfMonth(day);
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        }
        return input.with(date);
    }
}

package datetime.enums;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekAndMonthDemo {
    public static void main(String[] args) {
        // DayOfWeek
        for (DayOfWeek d : DayOfWeek.values()) {
            System.out.print(d + " ");
        }
        System.out.println();
        // 以用户所在区域的语言显示星期几
        for (DayOfWeek d : DayOfWeek.values()) {
            System.out.print(d.getDisplayName(TextStyle.SHORT, Locale.TRADITIONAL_CHINESE) + " ");
            ;
        }
        System.out.println();

        for (Month m : Month.values()) {
            System.out.print(m.getDisplayName(TextStyle.FULL, Locale.CHINA) + " ");
        }
        System.out.println();
    }
}

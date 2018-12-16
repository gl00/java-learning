package datetime.temporal.query;

import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class FamilyBirthdays {
  // Returns true if the passed-input date is the same as one of the
  // family birthdays. Because the query compares the month and day only,
  // the check succeds even if the Temporal types are not the same.
  public static Boolean isFamilyBirthday(TemporalAccessor date) {
    int month = date.get(ChronoField.MONTH_OF_YEAR);
    int day = date.get(ChronoField.DAY_OF_MONTH);

    // Angie's birthday is on April 3.
    if (month == Month.APRIL.getValue() && day == 3) {
      return Boolean.TRUE;
    }

    // Sue's birthday is on June 18.
    if (month == Month.JUNE.getValue() && day == 18) {
      return Boolean.TRUE;
    }

    // Joe's birthday is on May 29.
    if (month == Month.MAY.getValue() && day == 29) {
      return Boolean.TRUE;
    }

    return Boolean.FALSE;
  }
}

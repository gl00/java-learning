package basic.annotation.repeating_annotation;

public class RepeatingAnnotationDemo {
    @Schedule(dayOfMonth = "last")
    @Schedule(dayOfWeek = "Fri", hour = 23)
    public void doPeriodicCleanup() {
        // ...
    }
}

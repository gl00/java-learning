package essential_classes.concurrency.thread_objects;

public class SleepMessage {
    public static void main(String[] args) throws InterruptedException {
        String[] msgs = {"Good morning!", "Good afternoon!", "Good evening!", "Good night!"};
        for (int i = 0; i < msgs.length; i++) {
            Thread.sleep(4000);
            System.out.println(msgs[i]);
        }
    }
}

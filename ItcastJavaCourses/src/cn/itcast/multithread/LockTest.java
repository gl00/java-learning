package cn.itcast.multithread;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Admin on 9/1/2016.
 */
public class LockTest {
    // 共享数据
    private String message;

    private final Lock lock = new ReentrantLock();

    public void printMessageWithLock(String msg) {
        lock.lock();
        try {
            this.message = msg;
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s: %s%n", Thread.currentThread().getName(), message);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockTest app = new LockTest();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                app.printMessageWithLock("ooooooooooooooooooooooo");
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                app.printMessageWithLock("xxxxxxxxxxxxxxxxxxxxxxx");
            }
        }, "B").start();

    }

}

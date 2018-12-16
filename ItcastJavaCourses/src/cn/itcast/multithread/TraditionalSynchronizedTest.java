package cn.itcast.multithread;

import java.util.Random;

/**
 * Created by Admin on 9/1/2016.
 */
public class TraditionalSynchronizedTest {
    // 共享数据
    private String message;

    // 没有同步，多个线程同时操作共享数据会互相影响
    public void printMessage(String msg) {
        this.message = msg;

        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("%s: %s%n", Thread.currentThread().getName(), message);
    }

    public synchronized void printMessageWithSyn(String msg) {
        this.message = msg;

        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("%s: %s%n", Thread.currentThread().getName(), message);
    }

    // 特别注意：多个线程要实现同步，必须竞争去获得同一把锁。如果线程们去获得不同的锁，就没有同步效果了。


    public void printMessageWithSyn2(String msg) {
        // 使用synchronized语句显示指定使用哪个对象的锁
        synchronized (this) {
            this.message = msg;

            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("%s: %s%n", Thread.currentThread().getName(), message);
        }
    }

    public static void main(String[] args) {
//        testNoSyn();
        testSyn();
    }

    private static void testNoSyn() {
        TraditionalSynchronizedTest app = new TraditionalSynchronizedTest();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                app.printMessage("ooooooooooooooooooooooo");
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                app.printMessage("xxxxxxxxxxxxxxxxxxxxxxx");
            }
        }, "B").start();
    }

    private static void testSyn() {
        TraditionalSynchronizedTest app = new TraditionalSynchronizedTest();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                app.printMessageWithSyn("ooooooooooooooooooooooo");
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                app.printMessageWithSyn("xxxxxxxxxxxxxxxxxxxxxxx");
            }
        }, "B").start();
    }
}

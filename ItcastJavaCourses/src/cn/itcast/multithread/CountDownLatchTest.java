package cn.itcast.multithread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Admin on 9/3/2016.
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    System.out.printf("%s: is waiting%n", Thread.currentThread().getName());
                    startSignal.await();

                    System.out.printf("%s: is working%n", Thread.currentThread().getName());
                    try {
                        Thread.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("%s: is finishing%n", Thread.currentThread().getName());
                    doneSignal.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // 主线程
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: is about to let threads work%n", Thread.currentThread().getName());
        startSignal.countDown();
        System.out.printf("threads are working%n");
        System.out.printf("%s: is waiting for threads to finish%n", Thread.currentThread().getName());
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: terminating%n", Thread.currentThread().getName());

    }
}

package cn.itcast.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 9/1/2016.
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
//        ExecutorService threadPool = Executors.newCachedThreadPool();
//        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10; i++) {
            int taskNum = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.printf("%d time loop in task %d%n", i, taskNum);
                    }
                }
            });
        }

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
//        scheduler.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("working...");
//            }
//        }, 5, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("working......");
            }
        }, 6, 2, TimeUnit.SECONDS);

    }
}

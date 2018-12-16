package cn.itcast.multithread.exercise;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Admin on 9/3/2016.
 * 现有代码模拟产生了16个日志对象，并且需要运行16秒才能打印完这些日志，请在程序中增加4个线程去调用parseLog()方法来分头打印这16个日志对象，
 * 程序只需要4秒即可打印完这些日志。
 * <p>
 * 我的解答。使用线程池。
 * <p>
 * 张孝祥老师给的答案是使用BlockingQueue存储任务，再用线程去处理。
 */
public class Test1 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.printf("Begin:%s%n", startTime / 1000);

//        ExecutorService threadPool = Executors.newFixedThreadPool(4); // 我的答案

        // 张老师的答案
        // 这里queue的容量只要大于等于1就行 因为只要线程领取了任务空出了位置，就可以在存入任务到队列
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(16);
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        parseLog(queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        // 模拟处理16个日志，下面的代码产生了16个日志对象，当前代码需要运行16秒才能打印完成这些日志。
        // 修改代码，开4个线程让这个16个对象在4秒钟打完
        for (int i = 0; i < 16; i++) { // 这行代码不能改动
            final String log = "" + (i + 1); // 这行代码不能改动
            {
//                parseLog(log);

                // 我的答案
//                threadPool.execute(() -> {
//                    parseLog(log);
//                });

                // 张老师的答案
                try {
                    queue.put(log);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

//        threadPool.shutdown();
    }

    // parseLog方法内部的代码不能改动
    public static void parseLog(String log) {
        System.out.printf("Log:%d%n", System.currentTimeMillis() / 1000);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

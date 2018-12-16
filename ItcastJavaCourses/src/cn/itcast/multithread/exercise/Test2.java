package cn.itcast.multithread.exercise;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Admin on 9/3/2016.
 * 现有代码不断产生数据，然后交给TestDo.doSome方法处理，就像生产者不断产生数据，消费者不断消费数据。
 * 请将代码改成有10个线程来消费数据，这些消费者都调用TestDo.doSome()方法来处理数据，故每个消费者都需要一秒才能处理完。
 * 程序应保证这些消费者线程依次有序地消费数据，只有上一个消费者消费完，下一个消费者才能消费。
 */
public class Test2 {

    public static void main(String[] args) {
        System.out.printf("Begin:%s%n", System.currentTimeMillis() / 1000);

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
//        Lock lock = new ReentrantLock();
        Semaphore semaphore = new Semaphore(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 要保证同时只有一个线程处理数据，可以使用Lock或semaphore
//                lock.lock();
                try {
                    semaphore.acquire();
                    String output = Test2Do.doSome(queue.take());
                    System.out.printf("%s:%s%n", Thread.currentThread().getName(), output);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    lock.unlock();
                    semaphore.release();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) { // 这里不能改动
            String input = i + " "; // 不能改动
//            String output = TestDo.doSome(input);
//            System.out.printf("%s:%s%n", Thread.currentThread().getName(), output);
            try {
                queue.put(input);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// 不能改动此TestDo类
class Test2Do {
    public static String doSome(String input) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return input + ":" + System.currentTimeMillis() / 1000;
    }
}


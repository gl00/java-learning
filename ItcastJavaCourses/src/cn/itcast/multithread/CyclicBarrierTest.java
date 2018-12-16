package cn.itcast.multithread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Admin on 9/3/2016.
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
//        CyclicBarrier barrier = new CyclicBarrier(5);
        CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            System.out.printf("所有线程都已到达。最后一个到达的线程是%s%n", Thread.currentThread().getName());
        });

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.printf("线程%s开始任务...%n", Thread.currentThread().getName());
                    Thread.sleep((long) (Math.random() * 1000)); // 模拟执行任务
                    System.out.printf("线程%s完成任务...%n", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    // 最后一个线程完成工作到达barrier和其他已完成各自工作在此等待的线程汇合，
                    // 此时，如果barrier构造器有第二个runnable参数，则最后到达的那个线程会去执行runnable的代码
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

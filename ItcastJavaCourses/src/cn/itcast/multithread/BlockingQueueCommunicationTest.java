package cn.itcast.multithread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Admin on 9/3/2016.
 * 用两个容量为1的队列实现同步通知功能
 */
public class BlockingQueueCommunicationTest {
    public static void main(String[] args) {
        Business business = new Business();

        new Thread(() -> {
            for (int i = 1; i <= 50; i++) {
                business.sub(i);
            }
        }).start();

        for (int i = 1; i <= 50; i++) {
            business.main(i);
        }
    }

    static class Business {
        private final BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<>(1);
        private final BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<>(1);

        {
            // queue2先放满
            try {
                queue2.put(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        void sub(int i) {
            try {
                // queue1初始为空，可以放入数据
                queue1.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < 10; j++) {
                System.out.printf("sub thread sequence of %d loop of %d%n", j, i);
            }

            try {
                // 释放queue2空间，让queue2的put操作不再阻塞
                queue2.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized void main(int i) {
            try {
                queue2.put(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < 100; j++) {
                System.out.printf("main thread sequence of %d loop of %d%n", j, i);
            }

            try {
                queue1.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

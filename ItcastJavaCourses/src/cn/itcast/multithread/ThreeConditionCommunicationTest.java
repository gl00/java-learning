package cn.itcast.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Admin on 9/2/2016.
 * 在TraditionalThreadCommunicationTest的基础上再加一个条件。
 * 让3个线程依次运行，一个10次，一个20次，一个100次。
 * 多个线程之间的通信，使用传统的Object的wait和notify不好实现，
 * 可以使用java.util.concurrent.Condition
 */
public class ThreeConditionCommunicationTest {
    public static void main(String[] args) {
        Business business = new Business();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                business.m2(i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                business.m3(i);
            }
        }).start();


        // 第一个线程（使用主线程）执行的代码
        // 这段代码必须放在另两个线程启动后面
        // 否则，这个循环结束后才会执行后面的代码
        for (int i = 0; i < 50; i++) {
            business.m1(i);
        }
    }

    private static class Business {
        private final Lock lock = new ReentrantLock();
        private final Condition condition1 = lock.newCondition();
        private final Condition condition2 = lock.newCondition();
        private final Condition condition3 = lock.newCondition();
        private int order = 1; // 第几个线程执行

        public void m1(int i) {
            lock.lock();
            try {
                while (order != 1) {
                    try {
                        condition1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 0; j < 10; j++) {
                    System.out.printf("m1 thread sequence of %d loop of %d%n", j, i);
                }
                // 唤醒第二个线程
                order = 2;
                condition2.signal();
            } finally {
                lock.unlock();
            }
        }

        public void m2(int i) {
            lock.lock();
            try {
                while (order != 2) {
                    try {
                        condition2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 0; j < 20; j++) {
                    System.out.printf("m2 thread sequence of %d loop of %d%n", j, i);
                }
                // 唤醒第三个
                order = 3;
                condition3.signal();
            } finally {
                lock.unlock();
            }
        }

        public void m3(int i) {
            lock.lock();
            try {
                while (order != 3) {
                    try {
                        condition3.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 0; j < 30; j++) {
                    System.out.printf("m3 thread sequence of %d loop of %d%n", j, i);
                }
                // 唤醒第一个
                order = 1;
                condition1.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}

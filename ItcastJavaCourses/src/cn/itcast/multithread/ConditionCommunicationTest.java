package cn.itcast.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Admin on 9/2/2016.
 */
public class ConditionCommunicationTest {
    public static void main(String[] args) {
        Business business = new Business();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                business.sub(i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                business.main(i);
            }
        }).start();
    }

    static class Business {
        private boolean bShouldSub = true;
        private final Lock lock = new ReentrantLock();
        private final Condition condition = lock.newCondition();

        public void sub(int i) {
            lock.lock();

            try {
                while (!bShouldSub) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int j = 0; j < 10; j++) {
                    System.out.printf("sub thread sequence of %d, loop of %d%n", j, i);
                }

                bShouldSub = false;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }

        public void main(int i) {
            lock.lock();
            try {
                while (bShouldSub) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int j = 0; j < 100; j++) {
                    System.out.printf("main thread sequence of %d, loop of %d%n", j, i);
                }

                bShouldSub = true;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}

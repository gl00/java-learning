package cn.itcast.multithread;

/**
 * Created by Admin on 8/31/2016.
 * <p>
 * 子线程循环10次，主线程循环100次，接着又回到子线程循环10次，接着再回到主线程循环100次，如此循环50次，请写出程序。
 * 分析：子线程和主线程之间要通信，可以使用wait，notify机制
 * 我的答案。传智播客张孝祥老师讲解的答案（TraditionalThreadCommunicationTest)
 */
public class ThreadLoopTest {

    public static void main(String[] args) {
        class Lock {
            private boolean condition = true;

            public boolean isCondition() {
                return condition;
            }

            public void setCondition(boolean condition) {
                this.condition = condition;
            }
        }
        class SubThread extends Thread {
            private Lock lock;
            private int count = 1;
            private final int loopTimes;

            public SubThread(Lock lock, int loopTimes) {
                this.lock = lock;
                this.loopTimes = loopTimes;
            }

            @Override
            public void run() {
                synchronized (lock) {
                    while (count <= loopTimes) {
                        while (!lock.isCondition()) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        String name = Thread.currentThread().getName();
                        for (int i = 1; i <= 10; i++) {
                            System.out.printf("%s %d: %d%n", name, count, i);
                        }
                        count++;
                        lock.setCondition(false);
                        lock.notify();
                    }
                }
            }
        }

        class MainThread extends Thread {
            private Lock lock;
            private int count = 1;
            private final int loopTimes;

            public MainThread(Lock lock, int loopTimes) {
                this.lock = lock;
                this.loopTimes = loopTimes;
            }

            @Override
            public void run() {
                synchronized (lock) {
                    while (count <= loopTimes) {
                        while (lock.isCondition()) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        String name = Thread.currentThread().getName();
                        for (int i = 1; i <= 100; i++) {
                            System.out.printf("%s %d: %d%n", name, count, i);
                        }
                        count++;
                        lock.setCondition(true);
                        lock.notify();
                    }
                }
            }
        }

        Lock lock = new Lock();
        new SubThread(lock, 50).start();
        new MainThread(lock, 50).start();
    }
}

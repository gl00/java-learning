package cn.itcast.multithread;

/**
 * Created by Admin on 8/31/2016.
 * 设计4个线程，其中两个线程每次对j加1，另外两个线程每次对j减1。
 */
public class MultiThreadShareDataTest {
    class SharedData {
        private int j;

        public int get() {
            return j;
        }

        public synchronized void increment() {
            j++;
        }

        public synchronized void decrement() {
            j--;
        }
    }

    class Increment implements Runnable {
        private SharedData sharedData;

        public Increment(SharedData sharedData) {
            this.sharedData = sharedData;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                sharedData.increment();
                System.out.printf("%s: %d%n", Thread.currentThread().getName(), sharedData.get());
            }
        }
    }

    class Decrement implements Runnable {
        private SharedData sharedData;

        public Decrement(SharedData sharedData) {
            this.sharedData = sharedData;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                sharedData.decrement();
                System.out.printf("%s: %d%n", Thread.currentThread().getName(), sharedData.get());
            }
        }
    }

    public static void main(String[] args) {
        MultiThreadShareDataTest app = new MultiThreadShareDataTest();
        SharedData sharedData = app.new SharedData();
        new Thread(app.new Increment(sharedData)).start();
        new Thread(app.new Decrement(sharedData)).start();
    }
}

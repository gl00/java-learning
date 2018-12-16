package cn.itcast.multithread;

import java.util.Random;

/**
 * Created by Admin on 8/31/2016.
 * <p>
 * 使用ThreadLocal来改写ThreadScopeSharedData
 */
public class ThreadLocalTest {
    private static ThreadLocal<Integer> myThreadLocal = new ThreadLocal();

    private static class A {
        void printData() {
            System.out.printf("A %s: %d%n", Thread.currentThread().getName(), myThreadLocal.get());
        }
    }

    private static class B {
        void printData() {
            System.out.printf("B %s: %d%n", Thread.currentThread().getName(), myThreadLocal.get());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    System.out.printf("%s put data: %d%n", Thread.currentThread().getName(), data);
                    myThreadLocal.set(data);

                    new A().printData();
                    new B().printData();
                }
            }).start();
        }
    }
}

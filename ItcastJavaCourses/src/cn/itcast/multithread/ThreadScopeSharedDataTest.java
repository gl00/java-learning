package cn.itcast.multithread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Admin on 8/31/2016.
 * <p>
 * 同一个线程内多次读取到的数据应该保持一致。
 * 这里，同一个线程内，A和B读取到的data应该和存入的data是同一个值。
 */
public class ThreadScopeSharedDataTest {
    // 保存每个线程的数据
    private static Map<Thread, Integer> threadData = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    System.out.printf("%s has put data: %d%n", Thread.currentThread().getName(), data);
                    threadData.put(Thread.currentThread(), data);

                    new A().printData();
                    new B().printData();
                }
            }).start();
        }
    }

    static class A {
        public void printData() {
            System.out.printf("A %s: %d%n",
                    Thread.currentThread().getName(),
                    threadData.get(Thread.currentThread()));
        }
    }

    static class B {
        public void printData() {
            System.out.printf("B %s: %d%n",
                    Thread.currentThread().getName(),
                    threadData.get(Thread.currentThread()));
        }
    }
}

package cn.itcast.multithread;

import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * Created by Admin on 9/3/2016.
 */
public class ExchangerTest {
    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger();

        class Worker implements Runnable {
            private String data;

            public Worker(String data) {
                this.data = data;
            }

            @Override
            public void run() {
                System.out.printf("线程%s准备交换数据%s%n", Thread.currentThread().getName(), data);
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.printf("线程%s换回的数据为%s%n",
                            Thread.currentThread().getName(),
                            exchanger.exchange(data));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 注意：线程个数为奇数个时，有一个线程没有交换对象会一直等待
        new Thread(new Worker("aaa")).start();
        new Thread(new Worker("bbb")).start();
        new Thread(new Worker("ccc")).start();
    }
}

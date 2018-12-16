package cn.itcast.multithread;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Admin on 9/1/2016.
 * <p>
 * 使用读写锁确保任何时刻都只允许最多一个线程对共享数据做写操作，但允许同时有多个线程读取共享数据。
 * 一个线程读的时候其他线程不能写，但可以读。
 * 一个线程写的时候其他线程不能写也不能读。
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        SharedData sharedData = new ReadWriteLockTest().new SharedData();

        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        sharedData.getData();
                    }
                }
            }, "reader " + i).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        sharedData.setData(new Random().nextInt(99999));
                    }
                }
            }, "writer " + i).start();
        }
    }

    private class SharedData {
        private int data;

        private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

        public int getData() {
            rwLock.readLock().lock();
            try {
                String name = Thread.currentThread().getName();
                System.out.printf("%s: ready to read. now data = %s%n", name, data);
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s: finished reading. now data = %s%n", name, data);

                return data;
            } finally {
                rwLock.readLock().unlock();
            }
        }

        public void setData(int data) {
            rwLock.writeLock().lock();
            try {
                String name = Thread.currentThread().getName();
                System.out.printf("%s: ready to write. now data = %s%n", name, data);
                this.data = data;
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s: finished writing. now data = %s%n", name, data);
            } finally {
                rwLock.writeLock().unlock();
            }
        }
    }
}

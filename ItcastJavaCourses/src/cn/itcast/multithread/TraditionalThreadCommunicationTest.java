package cn.itcast.multithread;

/**
 * Created by Admin on 8/31/2016.
 * 子线程循环10次，主线程循环100次，接着又回到子线程循环10次，接着再回到主线程循环100次，如此循环50次，请写出程序。
 * <p>
 * 传智播客张孝祥老师讲解的答案。
 * 这里的关键点是：主线程和子线程的逻辑放到一个类的不同方法上，而不是分别写两个不同的类。因为它们之间需要通讯，必须持有相同的锁，把他们放在一个类
 * 里面可以大大简化代码。
 */

public class TraditionalThreadCommunicationTest {
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
        private boolean bShouldSub = true;

        synchronized void sub(int i) {
            // wait应该总是放在一个while循环里面（参考api）
            while (!bShouldSub) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 0; j < 10; j++) {
                System.out.printf("sub thread sequence of %d loop of %d%n", j, i);
            }
            bShouldSub = false;
            this.notify();
        }

        synchronized void main(int i) {
            while (bShouldSub) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 0; j < 100; j++) {
                System.out.printf("main thread sequence of %d loop of %d%n", j, i);
            }
            bShouldSub = true;
            this.notify();
        }
    }
}

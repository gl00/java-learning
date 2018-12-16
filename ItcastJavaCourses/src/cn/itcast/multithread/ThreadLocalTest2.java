package cn.itcast.multithread;

import java.util.Random;

/**
 * Created by Admin on 8/31/2016.
 * 前面的ThreadLocalTest解决了在一个线程范围内的变量存入和读取一致性的问题。
 * 如果要有多个变量呢？
 * 可以把这些变量放到一个对象里面，然后把这个对象存入到ThreadLocal对象里。
 * 还有一个更好一点的办法（封装性更好，更加符合面向对象的方式）：这些变量都放入一个对象没错，进一步地，
 * 在这个对象里面来实现ThreadLocal的绑定。这样外面就不用操作ThreadLocal了。
 */
public class ThreadLocalTest2 {

    // 同一个线程内共享的数据（注意不是多个线程之间共享数据！）
    static class MyThreadScopeData {
        private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal();

        // 单例。只能通过getThreadInstance方法获取实例。
        private MyThreadScopeData() {
        }

        public static MyThreadScopeData getThreadInstance() {
            MyThreadScopeData instance = map.get();
            if (null == instance) {
                instance = new MyThreadScopeData();
                map.set(instance);
            }
            return instance;
        }

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    private static class A {
        void printData() {
            MyThreadScopeData myThreadScopeData = MyThreadScopeData.getThreadInstance();
            System.out.printf("A %s: name = %s, age = %d%n",
                    Thread.currentThread().getName(),
                    myThreadScopeData.getName(),
                    myThreadScopeData.getAge()
            );
        }
    }

    private static class B {
        void printData() {
            MyThreadScopeData myThreadScopeData = MyThreadScopeData.getThreadInstance();
            System.out.printf("B %s: name = %s, age = %d%n",
                    Thread.currentThread().getName(),
                    myThreadScopeData.getName(),
                    myThreadScopeData.getAge()
            );
        }
    }

    public static void main(String[] args) {
        Random random = new Random();

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 存入
                    int anInt = random.nextInt(100);
                    MyThreadScopeData.getThreadInstance().setName("name " + anInt);
                    MyThreadScopeData.getThreadInstance().setAge(anInt);
                    System.out.printf("%s put data: %d%n", Thread.currentThread().getName(), anInt);

                    // 读取
                    new A().printData();
                    new B().printData();

                    // ThreadLocal被封装在MyThreadScopeData里，对使用者透明。
                }
            }).start();
        }
    }
}

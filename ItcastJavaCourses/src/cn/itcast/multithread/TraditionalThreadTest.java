package cn.itcast.multithread;

/**
 * Created by Admin on 8/31/2016.
 */
public class TraditionalThreadTest {
    public static void main(String[] args) {
        // 继承Thread类
        new Thread(){
            @Override
            public void run() {
                System.out.println("Hello Thread");
            }
        }.start();

        // 实现Runnable接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello Runnable");
            }
        }).start();
    }
}

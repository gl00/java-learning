package essential_classes.concurrency.thread_objects;

import java.io.IOException;
import java.time.LocalTime;


/*
 * Thread的实例方法join，让当前线程等待调用join的线程对象执行。
 * t.join(); 		// 导致当前线程暂停执行，直到t的线程终止。
 * t.join(1000); 	// 导致当前线程暂停执行，直到1000毫秒后（只等t 1秒，1秒后当前线程继续执行，不管t是否还在执行）。
 */
public class JoinDemo {

  public static void main(String[] args) {

    Thread t = new Thread(new HelloRunnable());
    t.start();

    for (int i = 0; i < 10000; i++) {
      System.out.format("%-20s\t%10s\tbefore join\t%d%n",
          LocalTime.now(), Thread.currentThread().getName(), i);
    }

    try {
      /*
       * join之前，主线程和线程t都已经开始执行各自的for循环，二者之间互不影响，也就没有顺序关系了。
       * 实际上，如果两个循环都不是特别小，主线程的"before join"循环和线程t的"hello world"循环的执行会出现交叉，
       * 在本机测试发现操作系统会执行一会儿主线程，再执行一会线程t，再执行主线程， 再执行t…… 这样交替执行，
       * 这是操作系统通过时间切片功能分配每个线程执行时间。
       *
       * 在一个线程内部，是有执行顺序的。比如下面的"just before join ..."一定是在"before join"循环之后才执行。
       */
      System.out.format("%-20s\t%10s\tjust before join ...%n",
          LocalTime.now(), Thread.currentThread().getName());

      // 这里的join没有参数，主线程等待线程t执行完成后再继续执行主线程后面的代码
      // t.join();
      // 如果是为join指定时间参数，则该时间过后，主线程继续执行
      t.join(100);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }

    // join之后的代码会等到join完成后才执行。
    System.out.format("%-20s\t%10s\tafter join ...%n",
        LocalTime.now(), Thread.currentThread().getName());
  }

  private static class HelloRunnable implements Runnable {
    @Override
    public void run() {
      for (int i = 0; i < 10000; i++) {
        System.out.format("%-20s\t%10s\thello world\t%d%n",
            LocalTime.now(), Thread.currentThread().getName(), i);
      }
    }
  }
}

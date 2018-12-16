package cn.itcast.multithread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Admin on 9/2/2016.
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        final Pool pool = new SemaphoreTest().new Pool();
        ExecutorService[] executors = new ExecutorService[Pool.MAX_AVAILABLE + 1];
        for (int i = 0; i < executors.length; i++) {
            executors[i] = Executors.newSingleThreadExecutor();
            executors[i].execute(() -> {
                try {
                    while (true) {
                        String item = pool.getItem();
                        System.out.printf("%s acquiring %s%n", Thread.currentThread().getName(), item);
                        Thread.sleep(new Random().nextInt(300));
                        System.out.printf("%s putting back %s%n", Thread.currentThread().getName(), item);
                        pool.putItem(item);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // Semaphore用来控制某个资源可同时被多少个线程访问
    class Pool {
        private static final int MAX_AVAILABLE = 3;
        private final Semaphore available = new Semaphore(MAX_AVAILABLE);

        public String getItem() throws InterruptedException {
            available.acquire();
            return getNextAvailableItem();
        }

        public void putItem(String item) {
            // 只有items中的数据才被available限制线程访问数量
            // 所以只有item属于items才需要available释放出一个permit
            if (markAsUnused(item)) {
                available.release();
            }
        }

        private String[] items; // 共享资源
        private boolean[] used = new boolean[MAX_AVAILABLE]; // 标记items中的元素是否正在被某个线程访问

        Pool() {
            items = new String[MAX_AVAILABLE];
            for (int i = 0; i < MAX_AVAILABLE; i++) {
                items[i] = "ITEM " + i;
            }
        }

        private synchronized String getNextAvailableItem() {
            for (int i = 0; i < MAX_AVAILABLE; i++) {
                if (!used[i]) {
                    used[i] = true;
                    return items[i];
                }
            }
            return null;
        }

        private boolean markAsUnused(String item) {
            for (int i = 0; i < MAX_AVAILABLE; i++) {
                if (item == items[i]) {
                    if (used[i]) {
                        used[i] = false;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }
    }
}
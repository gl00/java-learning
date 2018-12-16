package cn.itcast.multithread;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Admin on 9/1/2016.
 */
public class CacheDemo {
    public static void main(String[] args) {

    }

    class CacheData {
        private Object data = null;
        private volatile boolean cacheValid;
        private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

        public void processCachedData() {
            rwl.readLock().lock();
            if (!cacheValid) {
                // Must release read lock before acquiring write lock
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try {
                    // Recheck state because another thread might have
                    // acquired write lock and changed state before we did.
                    if (!cacheValid) {
                        data = "hello"; // 实际上可能要查数据库等
                        cacheValid = true;
                    }
                    // Downgrade by acquiring read lock before releasing write lock
                    rwl.readLock().lock();
                } finally {
                    rwl.writeLock().unlock(); // Unlock write, still hold read
                }
            }

            try {
                use(data);
            } finally {
                rwl.readLock().unlock();
            }
        }

        private void use(Object data) {
            //
        }
    }
}

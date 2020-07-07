package LockTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Lock java中的显式锁
 * 相关API:
 *
 */
public class Example1 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        IntStream.range(0, 3).forEach(i -> {
            new Thread() {
                @Override
                public void run() {
                    try {
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + " get the lock!");
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }

                }
            }.start();

        });

    }
}

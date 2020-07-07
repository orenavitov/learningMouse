package SemaphoreTest;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 使用Semaphore 制作一个显示锁
 * Sempahore 是信号量的意思， Semphore会维护一些信号量供线程申请
 */
public class Example1 {
    public static void main(String[] args) {
        SemaphoreLock lock = new SemaphoreLock();

        IntStream.range(0, 2).forEach(i -> {
            new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is going to get the lock.");
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + " get the lock.");
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();

                    }
                    System.out.println(Thread.currentThread().getName() + " release the lock.");
                }
            }.start();
        });



    }

    static class SemaphoreLock {
        private Semaphore semaphore = new Semaphore(1);

        public void lock() {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void unlock() {
            semaphore.release();
        }
    }
}

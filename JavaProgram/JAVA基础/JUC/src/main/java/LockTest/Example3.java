package LockTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * 使用读写锁实现多个读线程和多个写线程
 */
public class Example3 {

    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    private final static Lock readLock = lock.readLock();

    private final static Lock writeLock = lock.writeLock();

    private static int number = 1;

    public static void main(String[] args) {
        IntStream.range(0, 2).forEach(i -> {
            new Thread("writer" + i) {
                @Override
                public void run() {
                    write();
                }
            }.start();
        });
        IntStream.range(0, 20).forEach(i -> {
            new Thread("reader" + i) {
                @Override
                public void run() {
                    read();
                }
            }.start();
        });
    }

    private static void read() {

        while (true) {
            try {
                readLock.lock();
                System.out.println(Thread.currentThread().getName() + " read! " + " ===== " + number);
            } finally {
                readLock.unlock();
            }
        }

    }

    private static void write() {
        while (true) {
            try {
                writeLock.lock();
                number ++;
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + " write! " + " ===== " + number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }
    }
}

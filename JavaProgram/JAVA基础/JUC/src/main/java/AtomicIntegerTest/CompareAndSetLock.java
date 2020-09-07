package AtomicIntegerTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用CompareAndSet实现一个显示锁
 */
public class CompareAndSetLock {

    private final AtomicInteger LOCK = new AtomicInteger(0);

    private Thread LOCKThread = null;

    public void tryLock() throws Exception {

        while (!LOCK.compareAndSet(0, 1)) {

        }
        LOCKThread = Thread.currentThread();
    }

    public void unLock() {
        if (0 == LOCK.get()) {
            return;
        }
        if (Thread.currentThread() == LOCKThread) {

            LOCK.compareAndSet(1, 0);
        }

    }

    public static void main(String[] args) {
        CompareAndSetLock lock = new CompareAndSetLock();
        new Thread(() -> {
            try {
                lock.tryLock();
                System.out.println(Thread.currentThread().getName() + " get the lock");
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.tryLock();
                System.out.println(Thread.currentThread().getName() + " get the lock");
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
            }
        }).start();
    }
}

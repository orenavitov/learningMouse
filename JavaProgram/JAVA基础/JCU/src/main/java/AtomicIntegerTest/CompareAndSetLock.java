package AtomicIntegerTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用CompareAndSet实现一个显示锁
 */
public class CompareAndSetLock {

    private final AtomicInteger LOCK = new AtomicInteger(0);

    private Thread LOCKThread = null;

    public void tryLock() throws Exception {
        boolean result = LOCK.compareAndSet(0, 1);
        if (result) {
            LOCKThread = Thread.currentThread();
        }
        if (!result) {
            throw new Exception();
        }
    }

    public void unLock() {
        if (0 == LOCK.get()) {
            return;
        }
        if (Thread.currentThread() == LOCKThread) {

            LOCK.compareAndSet(1, 0);
        }

    }
}

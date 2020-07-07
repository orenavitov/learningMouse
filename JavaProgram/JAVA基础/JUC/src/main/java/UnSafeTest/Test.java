package UnSafeTest;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) throws InterruptedException, NoSuchFieldException {
        // 正常无法获得UnSage
//        Unsafe unsafe = Unsafe.getUnsafe();
//        System.out.println(unsafe);
        // 通过反射获得
//        Unsafe unsafe = getUnSafe();
//        System.out.println(unsafe);
        /**
         * ErrorCounter :
         *  Counter result : 97061492
         *  Cost time : 327ms
         *
         * SynConter:
         *  Counter result : 100000000
         *  Cost time : 2911ms
         *
         * LockCounter:
         *  Counter result : 100000000
         *  Cost time : 3257ms
         */
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
//        Counter counter = new ErrorCounter();
//        Counter counter = new SynCounter();
//        Counter counter = new LockCounter();
        Counter counter = new CASCounter();
        CounterRunnable counterRunnable = new CounterRunnable(counter, 100000);
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i ++) {
            executorService.submit(counterRunnable);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        Long endTime = System.currentTimeMillis();
        System.out.println("Counter result : " + counter.getCounter());
        System.out.println("Cost time : " + (endTime - startTime));
    }

    private static Unsafe getUnSafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    interface Counter {
        void increment();
        int getCounter();
    }

    static class CounterRunnable implements Runnable {

        private final Counter counter;
        private final int num;

        CounterRunnable(Counter counter, int num) {
            this.counter = counter;
            this.num = num;
        }

        @Override
        public void run() {
            for (int i = 0; i < num; i++) {
                counter.increment();
            }
        }
    }

    static class ErrorCounter implements Counter {

        private int counter = 0;

        @Override
        public void increment() {
            counter ++;
        }

        @Override
        public int getCounter() {
            return counter;
        }
    }

    static class SynCounter implements Counter {

        private int counter = 0;

        @Override
        public synchronized void increment() {
            counter ++;
        }

        @Override
        public int getCounter() {
            return counter;
        }
    }

    static class LockCounter implements Counter {

        private int counter = 0;
        private Lock lock = new ReentrantLock();

        @Override
        public void increment() {
            try {
                lock.lock();
                counter ++;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public int getCounter() {
            return counter;
        }
    }

    static class CASCounter implements Counter {

        private volatile int counter = 0;
        private Unsafe unsafe;
        private long offset;
        CASCounter() throws NoSuchFieldException {
            unsafe = getUnSafe();
            offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
        }

        @Override
        public void increment() {
            int current = counter;
            while (!unsafe.compareAndSwapInt(this, offset, current, current + 1)) {
                current = counter;
            }
        }

        @Override
        public int getCounter() {
            return counter;
        }
    }
}

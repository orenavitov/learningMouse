package ThreadTest.NotifyTest;

public class Test1 {

    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread("T1") {
            @Override
            public void run() {
                synchronized (LOCK) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is doing sth.");
                        // wait 后会立刻释放锁
                        LOCK.wait();
                        Thread.sleep(10_000);
                        System.out.println(Thread.currentThread().getName() + " has done sth.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t2 = new Thread("T2") {
            @Override
            public void run() {
                synchronized (LOCK) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is doing sth.");
                        // 唤醒等待的线程后， 不会立刻释放锁
                        LOCK.notify();
                        Thread.sleep(10_000);
                        System.out.println(Thread.currentThread().getName() + " has done sth.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        t1.start();
        t2.start();
    }
}

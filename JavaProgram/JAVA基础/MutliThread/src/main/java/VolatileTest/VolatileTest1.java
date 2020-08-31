package VolatileTest;

/**
 * 并发中的三项原则：原子性， 有序性， 可见性
 * volatile可以保证操作的有序性、可见性
 * 可见性体现在：一个线程对volatile修饰的变量进行写操作后， 会对其他线程发出通知， 告诉其他线程该变量已经改变需要弃用线程缓存中的值，
 * 到主存中重新获得。但这并不能保证其他线程都能获得该变量的最新值，因为其他线程可能在读之后才收到这个通知；
 * 有序性体现在：①禁止重排序；②volatile的写操作一定发生在读操作之前；
 *
 */
public class VolatileTest1 {

    private static int INIT_VALUE = 0;

    private final static int MAX_LIMIT = 30;

    public static void main(String[] args) {

        Thread t1 = new Thread("T1") {
            @Override
            public void run() {
                int localValue = INIT_VALUE;
                while (localValue < MAX_LIMIT) {
                    int j = INIT_VALUE;
                    if (localValue != j) {
                        System.out.println(Thread.currentThread().getName() + ":" + "The value upated to " + INIT_VALUE);
                        localValue = INIT_VALUE;
                        System.out.println("localValue : " + localValue);
                    }

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        Thread t2 = new Thread("T2") {
            @Override
            public void run() {
                int localValue = INIT_VALUE;
                while (INIT_VALUE < MAX_LIMIT) {

                    System.out.println(Thread.currentThread().getName() + ":" + "Update the value to " + (++localValue));
                    INIT_VALUE = localValue;
                    try {
                        Thread.sleep(50);
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

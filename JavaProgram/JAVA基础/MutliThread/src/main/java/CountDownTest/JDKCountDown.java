package CountDownTest;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * 使用JDK自带的CountDown实现主线程等待所有的任务线程执行结束；
 *
 */
public class JDKCountDown {

    public static void main(String[] args) {

        final CountDownLatch countDownLatch = new CountDownLatch(5);

        IntStream.rangeClosed(1, 5).forEach(i -> {
            new Thread("Thread" + i) {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " is working!");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " done!");
                    countDownLatch.countDown();
                }
            }.start();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All work done!");
    }
}

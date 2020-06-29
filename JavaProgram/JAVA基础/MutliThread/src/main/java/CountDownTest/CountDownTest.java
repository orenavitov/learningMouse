package CountDownTest;

import java.util.stream.IntStream;

public class CountDownTest {

    public static void main(String[] args) {
        CountDown countDown = new CountDown(5);
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
                    countDown.down();
                }
            }.start();
        });
        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All work done!");
    }
}

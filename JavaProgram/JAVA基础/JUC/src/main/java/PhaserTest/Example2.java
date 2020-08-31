package PhaserTest;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 在同一条线程中重复使用同一个Phaser;
 */
public class Example2 {

    public static void main(String[] args) {
        Phaser phaser = new Phaser();
        IntStream.rangeClosed(1, 6).forEach(i -> {
            new SportsMan(i, phaser).start();
        });
    }

    private static class SportsMan extends Thread {
        private final int num;
        private final Phaser phaser;

        private SportsMan(int num, Phaser phaser) {
            this.num = num;
            this.phaser = phaser;
            this.phaser.register();
        }

        @Override
        public void run() {

            try {
                System.out.println(this.num + " is doing job1");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(this.num + " has done job1");

                phaser.arriveAndAwaitAdvance();

                System.out.println(this.num + " is doing job2");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(this.num + " has done job2");
                phaser.arriveAndAwaitAdvance();

                System.out.println(this.num + " is doing job3");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(this.num + " has done job3");
                phaser.arriveAndAwaitAdvance();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

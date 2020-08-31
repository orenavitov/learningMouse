package PhaserTest;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 类似CountLatchDown 但是可以动态的增加需要等待的part
 */
public class Example1 {

    public static void main(String[] args) {

        Phaser phaser = new Phaser();
        IntStream.rangeClosed(1, 5).forEach(i -> {
            new Task(phaser).start();
        });

        phaser.register();
        phaser.arriveAndAwaitAdvance();
        System.out.println("All work done!");

    }

    private static class Task extends Thread {

        private final Phaser phaser;

        private Task(Phaser phaser) {
            this.phaser = phaser;
            this.phaser.register();
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is working!");
            try {
                TimeUnit.SECONDS.sleep(3);
                this.phaser.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

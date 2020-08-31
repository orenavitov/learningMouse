package PhaserTest;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 如果其中一个工作线程down掉了， 这时其他线程等不到这个线程完成， 需要把这条down掉的线程去掉
 */
public class Example3 {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(2);

//        phaser.arriveAndAwaitAdvance();
//        System.out.println(phaser.getPhase());
//
//        phaser.arriveAndAwaitAdvance();
//        System.out.println(phaser.getPhase());
//
//        phaser.arriveAndAwaitAdvance();
//        System.out.println(phaser.getPhase());

        IntStream.rangeClosed(1, 2).forEach(i -> {
            new Task("Thread-" + i, phaser).start();
        });

//        try {
//            TimeUnit.SECONDS.sleep(2);
//            printPhaseInformation(phaser);
//            phaser.arriveAndAwaitAdvance();
//            printPhaseInformation(phaser);
//            System.out.println("end!");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private synchronized static void printPhaseInformation(Phaser phaser) {

        System.out.println("registered parties->" + phaser.getRegisteredParties());
        System.out.println("phase -> " + phaser.getPhase());
        System.out.println("arrived phase -> " + phaser.getArrivedParties());
        System.out.println("unarrived phase -> " + phaser.getUnarrivedParties());
    }


    private static class Task extends Thread {

        private String name;
        private Phaser phaser;



        private Task(String name, Phaser phaser) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
//                phaser.register();
//                printPhaseInformation(phaser);
                TimeUnit.SECONDS.sleep(2);
                phaser.arriveAndAwaitAdvance();
                printPhaseInformation(phaser);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " arrived!");

        }
    }
}

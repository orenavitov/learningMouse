package CyclicBarrierTest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(0);
        new Thread("T1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " finished!");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " : the other thread finished!");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }.start();

//        new Thread("T2") {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(6000);
//                    System.out.println(Thread.currentThread().getName() + " finished!");
//                    System.out.println("reset");
//                    cyclicBarrier.await();
//                    System.out.println(Thread.currentThread().getName() + " : the other thread finished!");
//                } catch (InterruptedException | BrokenBarrierException e) {
//                    e.printStackTrace();
//
//                }
//            }
//        }.start();
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(cyclicBarrier.getNumberWaiting());
//        System.out.println(cyclicBarrier.getParties());
//        // reset 后会进行重置， 使等待的parts置为0，但需要等待的线程为2， 这时候需要重新执行
//        cyclicBarrier.reset();
//        System.out.println(cyclicBarrier.getNumberWaiting());
//        System.out.println(cyclicBarrier.getParties());

    }
}

package ThreadTest.ProduceAndConsume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    private static final Object SHARE = new Object();

    private static final int MAX = 3;

    private static int current_num = 0;


    public static void main(String[] args) {
        List<Producer> producers = new ArrayList<Producer>();
        List<Consumer> consumers = new ArrayList<Consumer>();
        for (int i = 1; i <= 5; i++) {
            Producer producer = new Producer("P" + i);
            Consumer consumer = new Consumer("C" + i);
            producers.add(producer);
            consumers.add(consumer);
        }
        producers.forEach(p -> p.start());
        consumers.forEach(c -> c.start());

    }


    static class Producer extends Thread {

        public Producer(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                synchronized (SHARE) {
                    while (current_num >= MAX) {
                        try {
                            SHARE.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " will produce!");
                    current_num++;
                    System.out.println(Thread.currentThread().getName() + " has produced!" + " the current_num is " + current_num);
                    SHARE.notifyAll();
                }
            }
            }

    }

    static class Consumer extends Thread {
        public Consumer(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                synchronized (SHARE) {
                    while (current_num <= 0) {
                        try {
                            SHARE.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + " will consume!");
                    current_num--;
                    System.out.println(Thread.currentThread().getName() + " has consumed!" + " the current_num is " + current_num);
                    SHARE.notifyAll();
                }
            }

        }
    }

}

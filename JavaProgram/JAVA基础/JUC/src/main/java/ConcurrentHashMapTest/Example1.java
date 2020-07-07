package ConcurrentHashMapTest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Example1 {
    public static void main(String[] args) {
        ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
        AtomicInteger startNumber = new AtomicInteger(1);
        Thread t1 = new Thread() {
            @Override
            public void run() {

                while (true) {
                    int value = startNumber.getAndIncrement();

                    map.put(String.valueOf(value), value);
                    System.out.println(Thread.currentThread().getName() + " put " + value);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    int value = startNumber.getAndIncrement();
                    map.put(String.valueOf(value), value);
                    System.out.println(Thread.currentThread().getName() + " put " + value);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        Thread t3 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    int value = startNumber.getAndIncrement();
                    map.put(String.valueOf(value), value);
                    System.out.println(Thread.currentThread().getName() + " put " + value);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        Thread t4 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.print("the keys : ");
                    map.keySet().forEach(key -> {
                        System.out.print(" " + key + " ");
                    });
                    System.out.print("\n");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }


}

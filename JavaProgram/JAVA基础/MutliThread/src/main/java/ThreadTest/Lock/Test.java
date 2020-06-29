package ThreadTest.Lock;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        MihLock lock = new MihLock();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            Thread t = new Thread("T" + i) {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " will run!");
                    try {
                        lock.onLock();
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.release();
                    }
                }
            };
            threads.add(t);
        }
        threads.forEach(t -> t.start());
    }
}

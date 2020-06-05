package Singleton;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 500; i ++) {
            Thread t = new Thread("T" + i) {
                @Override
                public void run() {
                    Singleton7 singleton7 = Singleton7.getInstance();
                    System.out.println(Thread.currentThread().getName() + ":" + singleton7);
                }
            };
            threads.add(t);
        }
        threads.forEach(t -> {
            t.start();
        });
    }
}

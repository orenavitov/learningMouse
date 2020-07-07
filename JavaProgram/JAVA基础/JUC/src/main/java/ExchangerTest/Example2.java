package ExchangerTest;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class Example2 {
    public static void main(String[] args) {
        Exchanger<Object> exchanger = new Exchanger<>();
        new Thread("A") {
            @Override
            public void run() {
                try {
                    while (true) {
                        TimeUnit.SECONDS.sleep(2);
                        Object objA = new Object();
                        System.out.println(Thread.currentThread().getName() + " send " + objA);
                        Object objB = exchanger.exchange(objA);
                        System.out.println(Thread.currentThread().getName() + " recieves : " + objB);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread("B") {
            @Override
            public void run() {
                try {
                    while (true) {
                        TimeUnit.SECONDS.sleep(2);
                        Object objB = new Object();
                        System.out.println(Thread.currentThread().getName()+ " send " + objB);
                        Object objA = exchanger.exchange(objB);
                        System.out.println(Thread.currentThread().getName() + " recieves : " + objA);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

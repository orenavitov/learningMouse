package ExchangerTest;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class Example1 {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread("A") {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);

                    String message = exchanger.exchange("message form " + Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName() + " : " + message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread("B") {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);

                    String message = exchanger.exchange("message form " + Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName() + " : " + message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

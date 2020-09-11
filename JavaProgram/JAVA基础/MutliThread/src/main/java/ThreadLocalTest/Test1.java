package ThreadLocalTest;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal 可以在当前线程下保存一个数据（任意类型）， 只能通过当前线程获得
 */
public class Test1 {



    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadLocal<String> t1String = new ThreadLocal<>();
                t1String.set("t1");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " local : " + t1String.get());
            }
        },"t1");


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadLocal<String> t2String = new ThreadLocal<>();
                t2String.set("t2");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " local : " + t2String.get());
            }
        },"t2");

        t1.start();
        t2.start();
    }
}

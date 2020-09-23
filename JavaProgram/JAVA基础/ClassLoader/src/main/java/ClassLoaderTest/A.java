package ClassLoaderTest;

import java.util.concurrent.TimeUnit;

public class A {

    public synchronized void method1() {
        System.out.println(Thread.currentThread().getName() + "is excute method1.");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName() + "is excute method2.");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

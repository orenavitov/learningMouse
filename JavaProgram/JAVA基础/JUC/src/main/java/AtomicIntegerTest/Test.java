package AtomicIntegerTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;

public class Test {
    private final static String A = "A";
    private final static String B = "B";
    private final static AtomicMarkableReference<String> ar = new AtomicMarkableReference<>(A, false);
    public static void main(String[] args) {

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                if (ar.compareAndSet(A, B, false, true)) {
                    System.out.println("我是线程1,我成功将A改成了B");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                if (ar.compareAndSet(A, B, false, true)) {
                    System.out.println("我是线程2,我成功将A改成了B");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                if (ar.compareAndSet(B, A, true, false)) {
                    System.out.println("我是线程3,我成功将B改成了A");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                if (ar.compareAndSet(A, B, true, false)) {
                    System.out.println("我是线程4,我成功将A改成了B");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
}

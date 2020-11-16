package ThreadLocalTest;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal 可以在当前线程下保存一个数据（任意类型）， 只能通过当前线程获得
 * ThreadLocal 的原理：
 * 在Thread中有一个ThreadLocalMap, 保存了一个Entry的数组， 这个Entry数组中的每个entry都会保存相应的Key, value
 * 这个key是一个threadLocal的弱引用。
 * 为什么要使用弱引用：
 * 因为如果使用强引用会在这个线程未结束时始终存在这样一条引用链：
 * thread -> threadLocalMap -> threadLocal
 * 导致threadLocal会和这个thread的生命周期一样长， 无法被回收产生内存泄漏的问题
 */
public class Test1 {



    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadLocal<String> t1String = new ThreadLocal<>();
                ThreadLocal<String> t2String = new ThreadLocal<>();
                ThreadLocal<String> t3String = new ThreadLocal<>();
                t1String.set("t1");
                t2String.set("t2");
                System.gc();
                t3String.set("t3");

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

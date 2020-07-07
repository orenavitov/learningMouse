package ConditionTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Condition 类似于monitor 提供 await(), singal()等阻塞， 唤醒的方法
 * 下面使用Conition 实现一个多生产者多消费者的场景
 */
public class Example1 {

    private static final Lock lock = new ReentrantLock();

    private static final Condition condition = lock.newCondition();

    private static int count = 0;

    private static final int MAX_COUNT = 10;

    public static void main(String[] args) {
        IntStream.range(0, 2).forEach(i -> {
            new Thread("CreateThread-" + i) {
                @Override
                public void run() {
                    create();
                }
            }.start();
        });

        IntStream.range(0, 10).forEach(i -> {
            new Thread("ConsumeThread-" + i) {
                @Override
                public void run() {
                    consume();
                }
            }.start();
        });
    }

    static void create() {
        while (true) {
            try {
                lock.lock();
                while (count >= MAX_COUNT) {
                    condition.await();
                }
                count ++;
                System.out.println(Thread.currentThread().getName() + " create!  =======   " + count);
                TimeUnit.MILLISECONDS.sleep(100L);
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static void consume() {
        while (true) {
            try {
                lock.lock();
                while (count <= 0) {
                    condition.await();
                }
                count --;
                System.out.println(Thread.currentThread().getName() + " cosume!  =======   " + count);
                TimeUnit.MILLISECONDS.sleep(100L);
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

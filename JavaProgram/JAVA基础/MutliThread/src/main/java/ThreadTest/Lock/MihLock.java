package ThreadTest.Lock;

import java.util.ArrayList;
import java.util.List;

public class MihLock {

    private List<Thread> waitList = new ArrayList<>();

    private static volatile boolean USED = false;

    private Thread currentThread;

    synchronized public void onLock() {
        while (true) {
            waitList.remove(Thread.currentThread());
            if (USED) {
                try {
                    waitList.add(Thread.currentThread());
                    this.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " got the Lock!");

                currentThread = Thread.currentThread();
                waitList.remove(currentThread);
                System.out.println("The wait list : " + waitList);
                USED = true;
                return;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized public void release() {
        Thread t = Thread.currentThread();
        if (Thread.currentThread()==(currentThread)) {
            System.out.println(Thread.currentThread().getName() + " release the Lock!");
            this.notifyAll();
            USED = false;
        }
    }


}

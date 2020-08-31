package August;

import java.util.concurrent.TimeUnit;

public class TEST {

    private boolean flag = true;

    private void test() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean tempFlag1 = flag;
                boolean tempFlag2 = true;
                while (tempFlag2 == tempFlag1) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is running!");
                        TimeUnit.SECONDS.sleep(5);
                        tempFlag1 = flag;
                    } catch (InterruptedException e) {
                    }
                }
                System.out.println(Thread.currentThread().getName() + " is over!");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                flag = false;
            }
        });

        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();


    }



    public static void main(String[] args) {
        TEST test = new TEST();
        test.test();
    }

}

package ThreadTest.InterruptedTest;

public class Test1 {


    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " is interrupted");
                }
            }
        };
        t1.start();
        // interrupt 会发送一个中断信号， 线程会捕获这个中断信号， 进行中断异常处理；
        t1.interrupt();
    }
}

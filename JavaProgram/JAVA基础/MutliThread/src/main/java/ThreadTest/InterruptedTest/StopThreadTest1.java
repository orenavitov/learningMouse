package ThreadTest.InterruptedTest;
// 直接使用interrupted进行打断
public class StopThreadTest1 {
    public static void main(String[] args) {

        Thread t = new Thread("t") {
            @Override
            public void run() {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " is stopped!");
                }
            }
        };
        t.start();
        try {
            System.out.println("5 s later...");
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();

    }


}

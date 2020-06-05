package Tests;

public class AQS {
    public static void main(String[] args) {

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                LockTest.writer();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                LockTest.reader();
            }
        });
        thread1.start();
        thread2.start();

        System.out.println("hello world!");
    }
}

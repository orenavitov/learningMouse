public class HappenBeforeTest {
    private static volatile int a = 1;

    public static void main(String args[]) {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                a = 2;
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                System.out.println("a: " + a);
            }
        });
        thread2.start();
        thread1.start();

    }
}

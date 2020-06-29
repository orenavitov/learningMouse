package Tests;

public class DaemonThreadTest {
    public static void main(String[] args) {
        Thread thread = new Thread(new SubClass(), "DaemonThread");
        thread.setDaemon(true);
        thread.start();
    }

    static class SubClass implements Runnable {

        public void run() {
            try {
                Thread.sleep(100);
                System.out.println("SubClass");
            } catch (Exception e) {

            } finally {
                System.out.println("SubClass Final");
            }
        }
    }
}

package ThreadTest;

public class SleepTest {
    public static void main(String[] args) {
        try {
            Thread.sleep(1000_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package CountDownTest;

public class CountDown {

    private int total;
    private int counter;

    public CountDown(int total) {
        this.total = total;
    }

    public synchronized void down() {
        counter ++;
        this.notifyAll();
    }

    public synchronized void await() throws InterruptedException {
        while (counter != total) {
            this.wait();
        }

    }
}

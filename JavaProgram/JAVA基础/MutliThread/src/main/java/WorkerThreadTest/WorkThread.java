package WorkerThreadTest;

public class WorkThread extends Thread {

    private final Channel channel;

    public WorkThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            this.channel.take().execute();
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

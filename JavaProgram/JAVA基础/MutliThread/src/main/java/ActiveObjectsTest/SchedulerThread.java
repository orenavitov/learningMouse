package ActiveObjectsTest;

public class SchedulerThread extends Thread {
    private final ActivationQueue activationQueue;

    public SchedulerThread(ActivationQueue activationQueue) {
        this.activationQueue = activationQueue;
    }

    public synchronized void invoke(MethodRequest request) {
        activationQueue.put(request);
    }

    @Override
    public void run() {
        while (true) {
            activationQueue.take().execute();
        }
    }
}

package ActiveObjectsTest;

import java.util.LinkedList;

public class ActivationQueue {
    private final static int MAX_ACTIVATION_QUEUE_SIZE = 10;

    private final LinkedList<MethodRequest> methodQueue;

    public ActivationQueue() {
        this.methodQueue = new LinkedList<>();
    }

    public synchronized void put(MethodRequest methodRequest) {
        while (this.methodQueue.size() > MAX_ACTIVATION_QUEUE_SIZE) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        methodQueue.addLast(methodRequest);
        this.notifyAll();
    }

    public synchronized MethodRequest take() {
        while (this.methodQueue.size() <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MethodRequest request = this.methodQueue.removeFirst();
        this.notifyAll();
        return request;
    }
}

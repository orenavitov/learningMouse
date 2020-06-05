package GuardedSuspensionTest;

import java.util.LinkedList;

public class RequestQueue {

    private final LinkedList<Request> requests = new LinkedList<>();

    public Request getRequest() {
        synchronized (requests) {
            while (requests.size() <= 0) {
                try {
                    requests.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            return requests.removeFirst();
        }
    }

    public void putRequest(Request request) {
        synchronized (requests) {
            requests.addLast(request);
            requests.notifyAll();
        }
    }

}

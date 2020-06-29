package GuardedSuspensionTest;

public class Server extends Thread {

    private final RequestQueue requestQueue;

    public Server(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        while (true) {
            Request request = requestQueue.getRequest();
            System.out.println("Server -> " + request.getValue());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

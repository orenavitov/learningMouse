package GuardedSuspensionTest;

public class Client extends Thread {

    private final RequestQueue requestQueue;

    private final String value;

    public Client(RequestQueue requestQueue, String value) {
        this.requestQueue = requestQueue;
        this.value = value;
    }

    @Override
    public void run() {
        for (int i =0; i < 1000; i++) {
            System.out.println("Client request -> " + value);
            requestQueue.putRequest(new Request(value));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

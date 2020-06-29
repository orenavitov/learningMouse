package ThreadPerMessageTest;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageHandler {

    private Executor execute = Executors.newFixedThreadPool(5);

    public void request(Message message) {
        execute.execute(new Runnable() {
            @Override
            public void run() {
                String value = message.getValue();
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " done! " + "The value is : " + value);
            }
        });

    }

    public void finish() {
        ((ExecutorService)execute).shutdown();
    }
}

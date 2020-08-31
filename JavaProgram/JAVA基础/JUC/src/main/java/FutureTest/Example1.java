package FutureTest;

import java.util.concurrent.*;

public class Example1 {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("the work begin!");
//                while (Thread.interrupted()) {
//
//                }
                TimeUnit.SECONDS.sleep(10);
                System.out.println("the work is done!");
                return 10;
            }
        });
        TimeUnit.MILLISECONDS.sleep(10);
        boolean result = future.cancel(true);
        System.out.println("future cancel ? " + result);
        System.out.println("the work done? " + future.isDone());
        System.out.println("the main thread is over!");

    }
}

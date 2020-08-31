package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Example4 {
    private static void sleep(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * getNow(), 会先返回一个结果， 但future中的执行内容还会继续
     * complete(), 如果在调用complete()时， future中的内容还没有执行完， 终止future中的内容， 返回True， 否则返回False
     */
    private static void getNowAndComplete() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
           sleep(5);
            System.out.println(" continue.......");
            return "hello";
        });
        sleep(7);
        boolean result = future.complete("world");
//        String result = future.getNow("world");
        try {
            System.out.println("result : " + result);
            System.out.println("future get : " + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getNowAndComplete();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

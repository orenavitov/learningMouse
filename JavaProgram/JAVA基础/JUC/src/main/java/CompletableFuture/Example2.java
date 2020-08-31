package CompletableFuture;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class Example2 {

    /**
     * whenComplete 中的BiConsumer<? super T, ? super Throwable> v 是上一步的结果
     * whenComplete 与 whenCompleteAsync的区别：
     * （1）whenComplete 会阻塞， 就是在使用get()时会阻塞， whenCompleteAsync是异步的；
     */
    private static void supplyAsyncTest() {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return Thread.currentThread().getName() + " say hello";
        });
//                .whenComplete((v, t) -> {
//            System.out.println(v + " " + Thread.currentThread().getName() + " over");
//        });
        stringCompletableFuture.whenCompleteAsync((v, t) -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(v + " " + Thread.currentThread().getName() + " over");
        });
        try {
            System.out.println(stringCompletableFuture.get(5L, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

    }

    private static void thenApplyTest() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return "hello";
        }).thenApply(s -> {
            return s.length();
        });

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private static void handleTest() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync((Supplier<String>) () -> {
            throw new RuntimeException();
        }).handleAsync((v, t) -> {
            Optional.of(t).ifPresent(value -> {
                System.out.println(value + "error!");
            });
            return v == null ? 0 : v.length();
        });
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void thenAcceptTest() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "hello";
        });
//        completableFuture.thenAccept(s -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//                System.out.println("s length : " + s.length());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        completableFuture.thenAcceptAsync(s -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("s length : " + s.length());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            System.out.println(completableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        supplyAsyncTest();
        thenAcceptTest();
        Thread.currentThread().join();
    }
}

package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Example3 {

    private static void sleep(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 和AcceptBoth几乎一样
     */
    private static void combineTest() {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("first start");
            sleep(5);
            System.out.println("first end");
            return "mi";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("second start");
            sleep(5);
            System.out.println("second end");
            return "hao";
        }), (s1, s2) -> {
            return s1 + " " + s2;
        });
        try {
            System.out.println(stringCompletableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void thenBothAcceptTest() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("first start");
            sleep(5);
            System.out.println("first end");
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println("second start");
            sleep(10);
            System.out.println("second end");
            return 100;
        }), (s, i) -> {
            System.out.println(s + "--" + i);
        });
    }

    /**
     * 都会执行， 但最终只会消费其中的一个
     */
    private static void acceptEither() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("first start");
            sleep(5);
            System.out.println("first end");
            return "mi";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("second start");
            sleep(5);
            System.out.println("second end");
            return "hao";
        }), s -> {
            System.out.println("s lenght : " + s.length());
        });
    }

    private static void runAfterBothTest() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("first start");
            sleep(3);
            System.out.println("first end");
            return "mi";
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println("second start");
            sleep(5);
            System.out.println("second end");
            return "hao";
        }), () -> {
            System.out.println("all done");
        });
    }

    private static void runAfterEitherTest() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("first start");
            sleep(3);
            System.out.println("first end");
            return "mi";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("second start");
            sleep(5);
            System.out.println("second end");
            return "hao";
        }), () -> {
            System.out.println("one done");
        });
    }

    /**
     * 执行完第一个Completable， 在执行第二个Completable， 第一个Completable的结果作为第一个Completable的输入；
     */
    private static void composeTest() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("first start");
            sleep(3);
            System.out.println("first end");
            return "mi";
        })
                .thenCompose(s -> {
                    return CompletableFuture.supplyAsync(() -> {
                        System.out.println("second start");
                        sleep(5);
                        System.out.println("second end");
                        return s.length();
                    });
                });
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        composeTest();
        Thread.currentThread().join();
    }
}

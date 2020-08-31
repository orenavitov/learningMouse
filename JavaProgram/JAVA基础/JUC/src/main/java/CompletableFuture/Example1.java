package CompletableFuture;

import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * CompletableFuture 会自己创建一个线程池， 但这个线程池中的线程都是守护线程
 * CompletableFuture 相对于Future的好处：
 * 1. 不需要在get()的时候等， CompetableFutre可以设置完成时的回调函数
 * 2. 对于一组任务，完成一阶段再完成第二阶段时， 不会像invokeAll一样，要等第一阶段的任务全部结束了才能开始第二阶段
 * 3. 支持流式的编程
 */
public class Example1 {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

//        List<Callable<Integer>> callableList = IntStream.rangeClosed(1, 10).boxed().map(i -> (Callable<Integer>)()->{
//            return get();
//        }).collect(Collectors.toList());
//        List<Future<Integer>> futureList = executorService.invokeAll(callableList);
//        futureList.stream().map(future -> {
//            try {
//                return future.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }).filter(Objects::nonNull).parallel().forEach(data -> {
//            display(data);
//        });

//        CompletableFuture.runAsync(() -> {
//            int sleepTime = ThreadLocalRandom.current().nextInt(10);
//            try {
//                TimeUnit.SECONDS.sleep(sleepTime);
//                System.out.println(Thread.currentThread().getName() + " after sleep " + sleepTime + "s");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).whenComplete((v, t) -> {
//            System.out.println("done!");
//        });
//
//        System.out.println("main over!");

        IntStream.rangeClosed(1, 10).boxed().forEach(i -> CompletableFuture.supplyAsync(() -> get())
        .thenAccept(data -> display(data))
        .whenComplete((v, t) -> {
            System.out.println(i + " done!");
        }));
        Thread.currentThread().join();
    }



    private static int get() {
        int value = ThreadLocalRandom.current().nextInt(10);
        System.out.println(Thread.currentThread().getName() + " will sleep " + value + "s" + " in get");
        try {
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " after sleepping " + value + "s" + " in get");
        return value;
    }

    private static void display(int data) {
        System.out.println(Thread.currentThread().getName() + " will sleep " + data + "s" + " in display");
        try {
            TimeUnit.SECONDS.sleep(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " after sleepping " + data + "s" + " in display");
    }
}

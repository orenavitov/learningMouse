package ExexutorTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExecutorServiceTest {

    private static CountDownLatch state = new CountDownLatch(1);

    public static void main(String[] args) {
        invokeAllTest();
    }

    /**
     * 执行shutdown后就不能继续exexutor()一个新的runnable了
     */
    private static void isShutDownTest() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
//                state.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        boolean isShutdown = executorService.isShutdown();
        System.out.println("is shut down : " + isShutdown);

        executorService.shutdown();
//        executorService.execute(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
////                state.countDown();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });


//        isShutdown = executorService.isShutdown();
//        System.out.println("is shut down : " + isShutdown);
//        try {
//            state.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        boolean isTerminated = executorService.isTerminated();
//        System.out.println("is terminated : " + isTerminated);
    }

    private static void queTest() {
//        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);

        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                System.out.println("task1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                System.out.println("task2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        BlockingQueue<Runnable> que = executorService.getQueue();
        que.add(() -> {
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                System.out.println("other task");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * invokeAny(Collection<Callable>), 提交的callable中只要有一个完成，就返回， 不会等其他的Callable
     * invokeAny(Collaction<Callable>, time, TimeUntil), 在规定的时间内运行提交的Callable, 运行完几个算几个，
     * 但返回的值永远是第一个完成的
     */
    private static void InvokeAnyTest() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
        executorService.setKeepAliveTime(10, TimeUnit.SECONDS);
        executorService.allowCoreThreadTimeOut(true);
        List<Callable<Integer>> callableList = IntStream.range(0, 10).boxed().map(
                i -> (Callable<Integer>)()->{

                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
                    System.out.println(Thread.currentThread().getName() + " is working!");
                    return i;
                }
        ).collect(Collectors.toList());

        try {
            int value = executorService.invokeAny(callableList, 4 ,TimeUnit.SECONDS);
            System.out.println("value : " +value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    /**
     * 完成所有提交的任务
     */
    private static void invokeAllTest() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
        executorService.setKeepAliveTime(10, TimeUnit.SECONDS);
        executorService.allowCoreThreadTimeOut(true);

        List<Callable<Integer>> callables =  Arrays.asList((Callable<Integer>)() -> {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(" 5 done !");
            return 5;
        },
                (Callable<Integer>)() -> {
                    TimeUnit.SECONDS.sleep(7);
                    System.out.println(" 7 done !");
                    return 7;
                });

        try {
            List<Future<Integer>> futures = executorService.invokeAll(callables);
            System.out.println(futures.get(0).get());
            System.out.println(futures.get(1).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


//        List<Callable<Integer>> callableList = IntStream.range(0, 10).boxed().map(
//                i -> (Callable<Integer>)()->{
//
//                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(15));
//                    System.out.println(Thread.currentThread().getName() + " is working!");
//                    return i;
//                }
//        ).collect(Collectors.toList());
//
//        try {
//            List<Future<Integer>> futureList = executorService.invokeAll(callableList);
//            futureList.forEach(future -> {
//                try {
//                    int result = future.get();
//                    System.out.println("result : " + result);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            });
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}

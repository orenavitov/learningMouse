package ExexutorTest;

import java.util.concurrent.*;

/**
 *
 */
public class ExecutorsTest {
    public static void main(String[] args) {
        newSingleThreadScheduledExecutorTest();
    }

    /**
     * These pools will typically improve the performance
     * of programs that execute many short-lived asynchronous tasks.
     * ThreadPoolExecutor(0, Integer.MAX_VALUE,
     *                     60L, TimeUnit.SECONDS,
     *                     new SynchronousQueue<Runnable>());
     * 开始的线程池中没有线程，每提交一个Task， 先放入que中， 再从que中取出， 然后创建一个线程；
     * SynchronousQueue 中只能有一个元素
     */
    private static void newCachedThreadPoolTest() {
        ExecutorService executorService = Executors.newCachedThreadPool();
    }

    /**
     * ThreadPoolExecutor(nThreads, nThreads,
     *                    0L, TimeUnit.MILLISECONDS,
     *                    new LinkedBlockingQueue<Runnable>())
     * 核心线程数等于最大线程数
     */
    private static void newFixedThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

    }

    private static void newWorkStealingThreadPool() {
        ExecutorService executorService = Executors.newWorkStealingPool();
    }


    private static void newSingleThreadScheduledExecutorTest() {
        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        System.out.println("core Pool size : " +executorService.getCorePoolSize());
//        System.out.println("max Pool size : " +executorService.getMaximumPoolSize());
//        System.out.println("que size : " +executorService.getQueue().size());
//        System.out.println("active size : " +executorService.getActiveCount());
//
//        executorService.execute(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(20);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        System.out.println("core Pool size : " +executorService.getCorePoolSize());
//        System.out.println("max Pool size : " +executorService.getMaximumPoolSize());
//        System.out.println("que size : " +executorService.getQueue().size());
//        System.out.println("active size : " +executorService.getActiveCount());
    }
}

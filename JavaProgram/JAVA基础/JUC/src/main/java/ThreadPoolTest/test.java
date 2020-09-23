package ThreadPoolTest;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class test {

    private static class task extends Thread {

        private String name;

        private int val;
        public task(String name, int val) {
            super(name);
            this.val = val;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running! " +
                    " the handle val is " + val);
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Observer extends Thread {

        private ThreadPoolExecutor threadPoolExecutor;


        public Observer(String name) {
            super(name);
        }

        public void setObervered(ThreadPoolExecutor threadPoolExecutor) {
            this.threadPoolExecutor = threadPoolExecutor;
        }

        @Override
        public void run() {
            System.out.println("activate thread count : " + threadPoolExecutor.getActiveCount());
            System.out.println("task count : " + threadPoolExecutor.getTaskCount());
            System.out.println("que size : " + threadPoolExecutor.getQueue().size());
            System.out.println("pool size : " + threadPoolExecutor.getPoolSize());
        }
    }

    public static void main(String[] args) {
        int coreSize = 2;
        int maxSize = 4;
        int keepAliveTime = 10;
        BlockingQueue<Runnable> blockingDeque = new ArrayBlockingQueue<Runnable>(2);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(coreSize,
                maxSize, keepAliveTime, TimeUnit.SECONDS, blockingDeque);
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        IntStream.rangeClosed(1, 7).forEach(i -> {
            task t = new task("task-"+i, i);
            threadPoolExecutor.execute(t);

        });

        Observer observer = new Observer("observer");
        observer.setObervered(threadPoolExecutor);
        scheduledExecutorService.scheduleAtFixedRate(observer, 0, 4, TimeUnit.SECONDS);



    }
}

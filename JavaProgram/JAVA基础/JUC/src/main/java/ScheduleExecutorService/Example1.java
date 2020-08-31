package ScheduleExecutorService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Example1 {


    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        // 延迟2秒执行, 返回-个future
//        ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor.schedule(() -> {
//            System.out.println("after 2s!");
//        }, 2, TimeUnit.SECONDS);
//
//        // 延迟2秒执行， 使用Callable， 允许有返回值
//        ScheduledFuture<Integer> integerScheduledFuture = scheduledThreadPoolExecutor.schedule(() -> {
//            System.out.println("after 2s will return!");
//            return 2;
//        }, 2, TimeUnit.SECONDS);
//
//        try {
//            System.out.println(integerScheduledFuture.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        // 延迟5秒执行， 每2秒执行一次
        // 如果任务的执行时间大于周期时间， 那么必须等待任务执行完成才会开始新的周期
        // 假设5秒后开始一个任务， 任务执行需要5秒， 周期为2秒， 那么第一次执行为5秒后， 第二次执行为10秒后， 第三次执行为15秒后。。。

        ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            System.out.println("time: " + System.currentTimeMillis());

        }, 5, 2, TimeUnit.SECONDS);
        try {
            TimeUnit.SECONDS.sleep(10);
            scheduledFuture.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

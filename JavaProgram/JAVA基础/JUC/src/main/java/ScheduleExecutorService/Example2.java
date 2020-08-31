package ScheduleExecutorService;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Example2 {
    public static void main(String[] args) {
        AtomicInteger time = new AtomicInteger(0);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        /**
         * 在固定的延迟后开始执行下一次
         */
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            time.addAndGet(1);
            System.out.println(time.get() + " time work begin! at " +System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(time.get() + " time work after! at " +System.currentTimeMillis());
        }, 5, 2, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
        scheduledThreadPoolExecutor.shutdownNow();

    }
}

package CompletionServiceTest;

import java.sql.Time;
import java.util.concurrent.*;

/**
 * CompletionService 可以解决这样一个问题：
 * 假设有很多个任务， 每个任务执行的时间不尽相同， 有的执行时间长， 有的执行时间段，
 * 如果用ExecutorService的invokeAll, 那么可能会先get那些执行时间比较长的任务， 因为get会阻塞， 所以即使有执行时间比较短的任务完成
 * 了， 也获得不了结果；
 * CompletionService提供了Take() 和 pull()， 可以先获得执行时间比较短先完成的任务的结果；
 */
public class Example {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executor);
        completionService.submit((Callable<Integer>) () -> {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("after sleep 10S.");
            return 10;
        });
        completionService.submit((Callable<Integer>) () -> {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("after sleep 5S.");
            return 5;
        });
        Future<Integer> future = null;
        try {
            while ((future = completionService.take()) != null) {
                System.out.println(future.get());
            }
        } catch (Exception e) {

        }


    }
}

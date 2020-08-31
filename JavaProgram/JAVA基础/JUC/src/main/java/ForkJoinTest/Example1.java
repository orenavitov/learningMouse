package ForkJoinTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * 分而治之： 将一个任务分成几个子任务， 最后进行汇总
 *
 */
public class Example1 {

    private static final int MAX = 3;

    public static void main(String[] args) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> future = forkJoinPool.submit(new CalculatedRecursiveTask(0, 10));
        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class CalculatedRecursiveTask extends RecursiveTask<Integer> {

        private final int start;
        private final int end;

        public CalculatedRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if(end - start <= MAX) {
                return IntStream.rangeClosed(start, end).sum();
            } else {
                int middle = (end - start) / 2;
                CalculatedRecursiveTask leftTask = new CalculatedRecursiveTask(start, middle);
                CalculatedRecursiveTask rightTask = new CalculatedRecursiveTask(middle + 1, end);
                leftTask.fork();
                rightTask.fork();
                return leftTask.join() + rightTask.join();
            }
        }
    }
}

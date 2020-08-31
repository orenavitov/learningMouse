package ForkJoinTest;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Example2 {

    private final static int MAX = 3;

    private final static AtomicInteger SUM  = new AtomicInteger(0);

    public static void main(String[] args) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new CalculateRecursiveAction(0, 10));
        try {
            forkJoinPool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(SUM.get());
    }

    private static class CalculateRecursiveAction extends RecursiveAction {

        private final int start;

        private final int end;



        private CalculateRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
            System.out.println(Thread.currentThread().getName() + " start: " + start);
            System.out.println(Thread.currentThread().getName() + " end: " + end);
        }

        @Override
        protected void compute() {
            if(end - start <= MAX) {
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                int middle = (end - start) / 2;
                CalculateRecursiveAction leftTask = new CalculateRecursiveAction(start, middle);
                CalculateRecursiveAction rightTask = new CalculateRecursiveAction(middle + 1, end);
                leftTask.fork();
                rightTask.fork();

            }
        }
    }

}

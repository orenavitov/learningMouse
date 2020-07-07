package CountDownLatchTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    private static int[] numbers = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private static CountDownLatch latch = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < numbers.length; i++) {
            executorService.execute(new SampleRunnableClass(numbers, i, latch));
        }
        //使用线程池中的API
        // 关闭线程池， 但不会立刻关闭， 要等到线程池中所有的线程都执行完成
        //executorService.shutdown();
        // 这样可以阻塞当前线程， 等待executorService中的线程都执行完毕， 但不会永久的等， 这里等1小时
        //executorService.awaitTermination(1, TimeUnit.HOURS);

        //使用CountDownLatch
        latch.await();
        System.out.println("All done!");
        executorService.shutdown();

    }

//    static class SampleRunnableClass implements Runnable {
////
////        private int[] numbers;
////        private int index;
////
////        SampleRunnableClass(int[] numbers, int index) {
////            this.numbers = numbers;
////            this.index = index;
////        }
////
////        @Override
////        public void run() {
////            int value = numbers[index];
////            if (value / 2 == 0) {
////                value = value * 2;
////            }
////            try {
////                Thread.sleep(1000L);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            System.out.println(Thread.currentThread().getName() + " done " + index);
////        }
////    }
    static class SampleRunnableClass implements Runnable {

        private int[] numbers;
        private int index;
        private CountDownLatch latch;

        SampleRunnableClass(int[] numbers, int index, CountDownLatch latch) {
            this.numbers = numbers;
            this.index = index;
            this.latch = latch;
        }

        @Override
        public void run() {
            int value = numbers[index];
            if (value / 2 == 0) {
                value = value * 2;
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " done " + index);
            this.latch.countDown();
        }
    }
}

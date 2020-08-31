package ExexutorTest;

import java.util.concurrent.*;

public class ExecutorPoolTest {
    public static void main(String[] args) {

    }

    /**
     * ThreadPoolExecutor(int corePoolSize,
     *                               int maximumPoolSize,
     *                               long keepAliveTime,
     *                               TimeUnit unit,
     *                               BlockingQueue<Runnable> workQueue,
     *                               ThreadFactory threadFactory,
     *                               RejectedExecutionHandler handler
     * corePoolSize: 这线程池始终会有这么多线程， 即使这些线程是空闲的； 注意线程池在初始化时并不会直接初始化这么多线程，
     *               依然是提交一个创建一个， 直到max， 问题在于这些corePool不会消失， 而超过corePoolSize， 小于等于
     *               maxPoolSize的这一部分线程会在keepAliveTime后消失， 除非设置allowCoreThreadTimeOut
     * maximumPoolSize: 线程池可以拥有的最大的线程个数；
     * keepAliveTime: 当线程池中现有的线程数多于corePoolSize, 这些多于的线程可以存在的时间
     * workQueue：任务在执行前都会先加入这个que， 无论存不存在空闲的线程
     * handler：当达到maximumPoolSize， 或者workQueue满了以后的处理方法
     * corePool满了后并不会立刻增加线程池， 而是在que满了的时候才扩大线程池直到maximumPoolSize
     *
     * 终止一个线程池可以使用：
     * 1.
     * executor.shutdown();
     * executor.awaitTermination()
     * executor.shutdown()并不会立刻中断（对线程使用interruped）所有线程
     * 会先中断空闲的线程， 等在正在工作的现场执行结束
     * 2.
     * List<Runnable> tasks = executor.shutdownNow()
     * 会先返回que中还在等待的任务， 然后对所有Pool中的线程进行打断
     * @return
     */
    private static ExecutorService ExecutorPoolBuilder() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 2, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                }, new ThreadPoolExecutor.AbortPolicy()
        );
        return executor;
    }
}

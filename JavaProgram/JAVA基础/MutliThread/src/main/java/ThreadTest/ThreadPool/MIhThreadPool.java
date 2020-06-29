package ThreadTest.ThreadPool;

import java.util.ArrayList;
import java.util.List;

public class MIhThreadPool {

    private static final ThreadGroup DEFAULT_THREAD_GROUP = Thread.currentThread().getThreadGroup();

    private ThreadGroup threadGroup;

    private static final int DEFAULT_SIZE = 10;

    private int currrent_size = 0;

    private int size;

    private List<Runnable> tasks = new ArrayList<>();

    private List<Thread> threads = new ArrayList<>();

    public MIhThreadPool() {
        this(DEFAULT_THREAD_GROUP, DEFAULT_SIZE);
    }

    public MIhThreadPool(ThreadGroup threadGroup, int size) {
        this.size = size;
        this.threadGroup = threadGroup;
//        for (int i = 0; i < this.size; i++) {
//            Thread t = new Thread(threadGroup,"Thread" + i);
//            threads.add(t);
//        }
    }

    synchronized public void submitTask(Runnable task) {

        if (currrent_size <= size) {
            Thread t = new MihThread(threadGroup, task, "Thread" + currrent_size);
            ((MihThread)t).setThreadState(ThreadState.Free);
            threads.add(t);
        } else {
            System.out.println("执行队列已满！");
        }
    }

    public void excute() {
        threads.forEach(t -> {
            ((MihThread) t).setThreadState(ThreadState.WORKING);
            t.start();
        });
    }

    private enum ThreadState {
        Free,
        WORKING,
        BLOCK,
        DEAD;
    }

    private static class MihThread extends Thread {

        private ThreadState threadState;

        public MihThread() {
            super();
        }

        public MihThread(ThreadGroup threadGroup, Runnable task, String name) {
            super(threadGroup, task, name);
        }


        public ThreadState getThreadState() {
            return threadState;
        }

        public void setThreadState(ThreadState threadState) {
            this.threadState = threadState;
        }
    }

}

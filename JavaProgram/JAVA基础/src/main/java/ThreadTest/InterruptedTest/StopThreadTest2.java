package ThreadTest.InterruptedTest;

// 将任务作为一个执行线程的守护线程， 中断执行线程， 任务就会停止
public class StopThreadTest2 {

    public static void main(String[] args) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Begin do something");
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("END!");
            }
        };
        ThreadService threadService = new ThreadService();
        threadService.excute(task);
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("5 s later...");
        threadService.shutdown();
    }

    static class ThreadService {

        private Thread excuteThread;

        public void excute(Runnable task) {
            excuteThread = new Thread() {
                @Override
                public void run() {
                    Thread t = new Thread(task);
                    t.setDaemon(true);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {

                    }

                }
            };
            excuteThread.start();
        }

        public void shutdown() {
            excuteThread.interrupt();
        }

        public void shutdown(long million) {
            long currentTime = System.currentTimeMillis();
            long shutdownTime = currentTime + million;
            while (System.currentTimeMillis() <= shutdownTime) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("time out!");
            excuteThread.interrupt();
        }
    }
}

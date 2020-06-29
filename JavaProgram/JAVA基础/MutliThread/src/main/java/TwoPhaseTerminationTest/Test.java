package TwoPhaseTerminationTest;

/**
 * 线程结束后增加一个资源回收阶段
 */
public class Test {
    public static void main(String[] args) {
        MihThread thread = new MihThread();
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.shutdown();
    }
}

package TwoPhaseTerminationTest;

public class MihThread extends  Thread {

    private volatile boolean terminated = false;
    @Override
    public void run() {
        try {
            while (!terminated) {
                Thread.sleep(1_000);
                System.out.println(Thread.currentThread().getName() + " is working!");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            clean();
        }


    }

    private void clean() {
        try {
            Thread.sleep(2_000);
            System.out.println("Do some clean work!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        this.terminated = true;
        this.interrupt();
    }
}

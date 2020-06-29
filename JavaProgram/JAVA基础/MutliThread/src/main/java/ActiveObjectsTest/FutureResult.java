package ActiveObjectsTest;

public class FutureResult implements Result{

    private Result result;

    private boolean ready = false;

    public synchronized void setResult(Result result) {
        this.ready = true;
        this.result = result;
        this.notifyAll();
    }

    @Override
    public synchronized Object getResultValue() {
        while (!ready) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return this.result.getResultValue();
    }
}

package ReadWriteLock;

public class RWLock {
    private int readingReaders = 0;
    private int waitingReaders = 0;
    private int writingWriters = 0;
    private int waitingWriters = 0;
    private boolean preferWriter = true;

    public RWLock() {
        this(true);
    }

    public RWLock(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    public synchronized void readLock() {
        // 刚申请读锁
        this.waitingReaders ++;
        try {
            while (writingWriters > 0 && preferWriter) {
                this.wait();
            }
            // 申请到读锁开始读
//            System.out.println("R");
            this.readingReaders ++;
        } catch (Exception e) {

        } finally {
            // 读结束
            this.waitingReaders --;
        }
    }

    public synchronized void unReadLock() {
        this.readingReaders --;
        this.notifyAll();
    }

    public synchronized void writeLock() {
        this.waitingWriters ++;
        try {
            while (readingReaders > 0 || writingWriters > 0) {
                this.wait();
            }
            System.out.println("W");
            this.writingWriters ++;
        } catch (Exception e) {

        } finally {
            this.waitingWriters --;
        }
    }

    public synchronized void unWriteLock() {
        this.writingWriters --;
        this.notifyAll();
    }
}

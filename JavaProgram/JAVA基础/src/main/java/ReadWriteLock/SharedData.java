package ReadWriteLock;

public class SharedData {
    private final char[] buff;

    private final RWLock LOCK = new RWLock();

    public SharedData(int buffSize) {
        this.buff = new char[buffSize];
        for (int i = 0; i < buffSize; i++) {
            buff[i] = '*';
        }
    }

    public char[] read() {
        try {
            LOCK.readLock();
            return this.doRead();
        } finally {
            LOCK.unReadLock();
        }
    }

    public void write(char c, int index) {
        try {
            LOCK.writeLock();
            this.doWrite(c, index);
        } finally {
            LOCK.unWriteLock();
        }
    }

    private void doWrite(char c, int index) {
        this.buff[index] = c;
    }

    private char[] doRead() {
        char[] copy = new char[buff.length];
        for (int i = 0; i < buff.length; i++) {
            copy[i] = buff[i];
        }
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return copy;
    }
}

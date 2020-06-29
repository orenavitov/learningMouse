package WorkerThreadTest;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Channel {
    private final static int MAX_REQUEST = 100;

    private final Request[] requestQueue;

    private int head;

    private int tail;

    private int count;

    private final WorkThread[] workerPool;

    public Channel(int workers) {
        this.requestQueue = new Request[MAX_REQUEST];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
        this.workerPool = new WorkThread[workers];
        this.init();
    }

    private void init() {
        IntStream.range(0, workerPool.length).forEach(i -> {
            WorkThread workThread = new WorkThread("WorkThread-" + i, this);
            workerPool[i] = workThread;
        });
    }

    public void startWorker() {
        Arrays.asList(workerPool).forEach(Thread::start);
    }
    // 将货物放到传送带尾部
    public synchronized void put(Request request) {
        while (count >= requestQueue.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        requestQueue[tail] = request;
        this.count ++;
        this.tail = (tail + 1) % requestQueue.length;
        this.notifyAll();

    }

    // 从传送带的头部去货物给加工工人进行加工
    public synchronized Request take() {
        while (count <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Request request = requestQueue[head];
        count --;
        this.head = (head + 1) % requestQueue.length;
        this.notifyAll();
        return request;
    }
}

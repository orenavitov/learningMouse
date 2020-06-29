package WorkerThreadTest;

/**
 * Channel 是我们自定义的一个队列， 这里可以想象为一条传送带；
 * WorkThread 是我们的工作线程， 这里可以想象春传送带上的加工工人；
 * TranportThread 是我们的搬运工人， 将货物搬上传送带供工人进行加工；
 * 假设有这样一个场景：
 * 工厂中有1条传送带(Channel)， 有几名工人（WorkThred）， 有几名搬运工人（TransportThread），
 * 搬运工人将货物搬上传送带， 工人从传送带上去货物进行加工；
 */
public class Test {
    public static void main(String[] args) {
        Channel channel = new Channel(5);
        channel.startWorker();

        new TransportThread("Thread-1", channel).start();
        new TransportThread("Thread-2", channel).start();
        new TransportThread("Thread-3", channel).start();
    }
}

package ActiveObjectsTest;

public final class ActiveObjectFactory {
    private ActiveObjectFactory() {}

    public static ActiveObject createActiveObject() {
        Servant servant = new Servant();
        ActivationQueue queue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(queue);
        ActiveObjectProxy proxy = new ActiveObjectProxy(servant, schedulerThread);
        schedulerThread.start();
        return proxy;
    }
}

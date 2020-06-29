package ActiveObjectsTest;

class ActiveObjectProxy implements ActiveObject {

    private final Servant servant;
    private final SchedulerThread schedulerThread;

    public ActiveObjectProxy(Servant servant, SchedulerThread schedulerThread) {
        this.servant = servant;
        this.schedulerThread = schedulerThread;
    }

    //
    @Override
    public Result makeString(int count, char c) {
        FutureResult futureResult = new FutureResult();
        schedulerThread.invoke(new MakeStringResquest(servant, futureResult, count, c));
        return futureResult;
    }

    @Override
    public void displayString(String text) {
        schedulerThread.invoke(new DisplayTextRequest(servant, text));
    }
}

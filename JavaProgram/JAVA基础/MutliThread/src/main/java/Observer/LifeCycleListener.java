package Observer;

public interface LifeCycleListener {
    void onEvent(ObserverableRunnable.RunnableEvent event);
}

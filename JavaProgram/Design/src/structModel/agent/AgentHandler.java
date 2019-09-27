package structModel.agent;

public class AgentHandler implements Subject {

    private Handler handler;
    @Override
    public void task() {
        if (handler == null) {
            handler = new Handler();
        }
        preTask();
        handler.task();
        postTask();
    }
    // 前置任务
    private void preTask(){}
    // 后置任务
    private void postTask(){}
}

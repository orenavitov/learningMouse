package ThreadContextTest;

public class Action1 {

    public void execute() {
        try {
            Thread.sleep(1000L);
            ActionContext.getInstance().getContext().setName("mih");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

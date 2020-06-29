package ThreadContextTest;

public class Action2 {
    public void execute() {
        try {

            String name = ActionContext.getInstance().getContext().getName();
            Thread.sleep(1000L);
            ActionContext.getInstance().getContext().setAddress("Beijing");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

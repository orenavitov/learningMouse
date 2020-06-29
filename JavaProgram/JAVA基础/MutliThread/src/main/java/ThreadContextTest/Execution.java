package ThreadContextTest;

public class Execution implements Runnable {

    private Action1 action1 = new Action1();
    private Action2 action2 = new Action2();

    /**
     * context 相当于线程执行的上下文， 这个上下文在一个线程中时单例
     */
    @Override
    public void run() {
        Context context = ActionContext.getInstance().getContext();
        action1.execute();
        System.out.println("Name: " + context.getName() + " " + Thread.currentThread().getName());
        action2.execute();
        System.out.println("Address "+ context.getAddress() + " " + Thread.currentThread().getName());
    }
}

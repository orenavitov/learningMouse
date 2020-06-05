package SingletonGateTest;

public class Client {
    public static void main(String[] args) {
        Gate gate = new Gate();
        User bj = new User("BeiLao", "Beijing", gate);
        User sh = new User("ShangLao", "Shanghai", gate);
        User gz = new User("GuangLao", "Guangjing", gate);
        bj.start();
        sh.start();
        gz.start();
    }
}

package SingletonGateTest;

/**
 * 使用临界资源的线程
 */
public class User extends Thread {
    private final String name;
    private final String address;
    private final Gate gate;

    public User(String name, String address, Gate gate) {
        this.name = name;
        this.address = address;
        this.gate = gate;
    }

    @Override
    public void run() {
        while (true) {
            this.gate.pass(name, address);
        }
    }
}

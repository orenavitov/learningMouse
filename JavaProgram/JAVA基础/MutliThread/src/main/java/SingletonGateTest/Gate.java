package SingletonGateTest;

/**
 * Gate相当于临界区
 */
public class Gate {
    private int count = 0;
    private String name = "Nobody";
    private String address = "Nowhere";
    // 线程通过
    public void pass(String name, String address) {
        this.count ++;
        this.name = name;
        this.address = address;
        verify();
    }

    // 对通过的线程进行检查
    private void verify() {
        if (this.name.charAt(0) != this.address.charAt(0)) {
            System.out.println("**********ERROR************" + toString());
        }

    }

    public String toString() {
        return "N0." + count + ":" + name + "," + address;
    }

}

public class LockTest {

    int i;
    final int j;
    static LockTest lockTest;
    public LockTest() {
        i = 2;
        j = 2;
    }

    public static void writer() {
        lockTest = new LockTest();
    }

    public static void reader() {
        LockTest lock = lockTest;
        int a = lock.i;
        int b = lock.j;
        System.out.println("a: " + a);
        System.out.println("b: " + b);
    }




}

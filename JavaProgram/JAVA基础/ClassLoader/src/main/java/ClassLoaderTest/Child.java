package ClassLoaderTest;

public class Child extends Parent {
    public final static int childNum = 120;
    static {
        System.out.println("Child!");
    }
}

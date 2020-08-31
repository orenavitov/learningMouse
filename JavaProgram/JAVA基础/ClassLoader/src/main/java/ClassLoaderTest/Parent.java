package ClassLoaderTest;

public class Parent {

    public static int parnetNum = 110;

    static {
        System.out.println("Parent!");
    }
}

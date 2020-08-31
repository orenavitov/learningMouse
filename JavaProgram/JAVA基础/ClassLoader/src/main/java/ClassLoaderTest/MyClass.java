package ClassLoaderTest;

import java.util.Random;

public class MyClass {

    public static final int MyClassNum = 1;

    static {
        System.out.println("Load MyClass!");
    }
    public String hello() {
        return "Hello world";
    }
}

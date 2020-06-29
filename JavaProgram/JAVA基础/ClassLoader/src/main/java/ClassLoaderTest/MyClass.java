package ClassLoaderTest;

public class MyClass {
    static {
        System.out.println("Load MyClass!");
    }
    public String hello() {
        return "Hello world";
    }
}

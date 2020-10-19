package SomeTest;

public interface a {
    int val = 11;
    default void write() {
        System.out.println("write a");
    }
}

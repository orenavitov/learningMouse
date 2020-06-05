package FutureTest;

public interface Future<T> {
    T get() throws InterruptedException;
}

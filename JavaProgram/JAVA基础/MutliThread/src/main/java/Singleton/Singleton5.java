package Singleton;

/**
 * 解决懒惰单例的线程安全问题， 并且执行效率高， 解决空指针的问题；
 */
public class Singleton5 {
    // volatile 不仅可以保证在缓存的可见性， 还可以保证不会被重排序
    private static volatile Singleton5 singleton5;

    private Singleton5() {}

    public static Singleton5 getInstance() {

        if (singleton5 == null) {
            synchronized (Singleton4.class) {
                if (singleton5 == null) {
                    singleton5 = new Singleton5();
                }
            }

        }
        return singleton5;
    }
}

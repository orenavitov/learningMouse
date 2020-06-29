package Singleton;

/**
 * 解决懒惰单例的线程安全问题， 并且执行效率高， 但会引入空指针的问题；
 */
public class Singleton4 {
    private static Singleton4 singleton4;

    private Singleton4() {}

    public static Singleton4 getInstance() {

        if (singleton4 == null) {
            synchronized (Singleton4.class) {
                if (singleton4 == null) {
                    singleton4 = new Singleton4();
                }
            }

        }
        return singleton4;
    }
}

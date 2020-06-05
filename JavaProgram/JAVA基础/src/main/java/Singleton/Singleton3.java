package Singleton;

/**
 * 可以解决懒惰单例模式的线程安全问题， 执行效率很低；
 */
public class Singleton3 {
    private static Singleton3 singleton3;

    private Singleton3() {}

    public synchronized static Singleton3 getInstance() {
        if (singleton3 == null) {
            singleton3 = new Singleton3();
        }
        return singleton3;
    }
}

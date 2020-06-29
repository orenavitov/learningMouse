package Singleton;

/**
 * 饥饿单例模式
 * 每次使用Singleton1类， 都会触发singleton1的初始化；
 */
public class Singleton1 {

    private static Singleton1 singleton1 = new Singleton1();

    private Singleton1() {

    }

    public static Singleton1 getInstance() {
        return Singleton1.singleton1;
    }
}

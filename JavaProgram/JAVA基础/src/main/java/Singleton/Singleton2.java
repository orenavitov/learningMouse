package Singleton;

/**
 * 懒惰的单例模式， 只有在使用getInstance()时才会初始化singleton2
 * 问题是存在线程安全问题；
 */
public class Singleton2 {
    private static Singleton2 singleton2;

    private Singleton2() {}

    public static Singleton2 getInstance() {
        if (singleton2 == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            singleton2 = new Singleton2();
        }
        return singleton2;
    }
}

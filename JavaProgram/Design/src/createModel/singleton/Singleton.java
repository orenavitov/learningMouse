package createModel.singleton;

/**
 * 普通单例模式， 无法保证线程安全
 */
public class Singleton {
    private static Singleton singleton = null;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
}

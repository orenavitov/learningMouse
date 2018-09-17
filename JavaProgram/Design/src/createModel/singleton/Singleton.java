package createModel.singleton;

/**
 * 普通单例模式， 无法保证线程安全
 */
public class Singleton {
    private static Persion persion = null;

    private Singleton() {

    }

    public static Persion getInstance() {
        if (persion == null) {
            persion = new Persion();
        }
        return persion;
    }
}

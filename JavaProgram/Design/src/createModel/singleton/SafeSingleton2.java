package createModel.singleton;

/**
 *
 */
public class SafeSingleton2 {

    private static Persion persion = null;

    private SafeSingleton2() {

    }

    private static synchronized void init() {
        if (persion == null) {
            persion = new Persion();
        }
    }

    public static Persion getInstance() {
        if (persion == null) {
            init();
        }
        return persion;
    }
}

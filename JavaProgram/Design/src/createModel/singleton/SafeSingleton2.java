package createModel.singleton;

/**
 * 懒汉模式
 */
public class SafeSingleton2 {

    private static volatile SafeSingleton2 safeSingleton2 = null;

    private SafeSingleton2() {

    }


    public static synchronized SafeSingleton2 getInstance() {
        if (safeSingleton2 == null) {
            safeSingleton2 = new SafeSingleton2();
        }
        return safeSingleton2;
    }
}

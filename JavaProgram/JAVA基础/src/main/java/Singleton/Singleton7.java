package Singleton;

/**
 * 很推荐的一种做法， 原因在于枚举中的实例只会创建一次；
 */
public class Singleton7 {

    private Singleton7() {}

    private enum Builer {

        INSTANCE;
        Singleton7 singleton7;
        Builer() {
           singleton7 = new Singleton7();
        }

        Singleton7 getInstance() {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return singleton7;
        }
    }

    public static Singleton7 getInstance() {
        return Builer.INSTANCE.getInstance();
    }

}

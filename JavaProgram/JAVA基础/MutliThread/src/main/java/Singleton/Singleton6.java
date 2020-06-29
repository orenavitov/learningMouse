package Singleton;

/**
 * 推荐的一种方法；
 * 实现了懒惰单例， 解决了线程安全问题；
 */
public class Singleton6 {

    private Singleton6() {}

    private static class Singleton6Builder {
        private static Singleton6 singleton6 = new Singleton6();
        public static Singleton6 getInstance() {
            return singleton6;
        }
    }

    public static Singleton6 getInstance() {
        return Singleton6.getInstance();
    }

}

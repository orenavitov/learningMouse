package createModel.singleton;

public class SageSingleton1 {

    private SageSingleton1() {

    }

    private static class SingletonFactory {
        private static Persion persion = new Persion();
    }

    public static Persion getInstance() {
        return SingletonFactory.persion;
    }
}

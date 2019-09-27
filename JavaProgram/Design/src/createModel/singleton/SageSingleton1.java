package createModel.singleton;

/**
 *
 */
public class SageSingleton1 {

    private static SageSingleton1 sageSingleton1 = new SageSingleton1();
    private SageSingleton1() {
    }

    public static SageSingleton1 getInstance() {
        return sageSingleton1;
    }
}

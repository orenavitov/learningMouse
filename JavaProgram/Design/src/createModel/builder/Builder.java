package createModel.builder;
/**
 * 建造者模式
 */

import java.util.ArrayList;
import java.util.List;

public class Builder {

    private List<Animal> animals = new ArrayList<>();

    public void birdFactory(int count) {
        for (int i = 0; i < count; i ++) {
            animals.add(new Bird());
        }
    }

    public void dogFactory(int count) {
        for (int i = 0; i < count; i ++) {
            animals.add(new Dog());
        }
    }
}

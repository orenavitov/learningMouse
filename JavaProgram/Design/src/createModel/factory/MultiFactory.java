package createModel.factory;

import createModel.impl.GoodBye;
import createModel.impl.Hello;
import createModel.service.ActionService;

/**
 * 多工厂模式
 */
public class MultiFactory {

    public ActionService sayHello() {
        return new Hello();
    }

    public ActionService sayGoodBye() {
        return new GoodBye();
    }
}

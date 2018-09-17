package createModel.factory;

import createModel.impl.GoodBye;
import createModel.impl.Hello;
import createModel.service.ActionService;

/**
 * 静态工厂模式
 */
public class StaticFactory {

    public static ActionService sayHello() {
        return new Hello();
    }

    public static ActionService sayGoodBye() {
        return new GoodBye();
    }
}

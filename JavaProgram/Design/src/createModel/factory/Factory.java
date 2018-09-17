package createModel.factory;

import createModel.impl.GoodBye;
import createModel.impl.Hello;
import createModel.service.ActionService;

/**
 * 普通工厂模式
 */
public class Factory {

    public ActionService produce(String type) {
        if (type.equalsIgnoreCase("hello")) {
            return new Hello();
        } else if (type.equalsIgnoreCase("goodbye")) {
            return new GoodBye();
        } else {
            return null;
        }
    }
}

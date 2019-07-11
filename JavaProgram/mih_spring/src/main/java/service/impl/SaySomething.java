package service.impl;

import org.springframework.stereotype.Component;
import service.service.Say;

@Component(value = "say")
public class SaySomething implements Say {

    @Override
    public void sayHello() {
        System.out.println("hello!");
    }

    @Override
    public void sayGoodBye() {
    System.out.println("good bye!");
    }
}

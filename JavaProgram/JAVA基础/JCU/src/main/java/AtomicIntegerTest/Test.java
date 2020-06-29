package AtomicIntegerTest;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) {

        AtomicInteger i = new AtomicInteger(10);
//        int result1 = i.getAndAdd(1);
//        int result2 = i.get();
//        System.out.println(result1);
//        System.out.println(result2);
        //AtomicBoolean使用 1 表示true, 0 表示false, 如果不给初始值, 默认0 false;
        AtomicBoolean atomicBoolean = new AtomicBoolean();
    }
}

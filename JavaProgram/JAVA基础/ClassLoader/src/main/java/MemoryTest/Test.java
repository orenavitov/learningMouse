package MemoryTest;


import java.util.concurrent.TimeUnit;

public class Test {

    private static void method() {
        Demo demo = new Demo();
        demo.setA(99);
        try {
            TimeUnit.SECONDS.sleep(999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        method();
        try {
            TimeUnit.SECONDS.sleep(999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

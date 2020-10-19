package ClassLoaderTest;

import java.io.File;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        String property = System.getProperty("sun.boot.class.path");
//        String[] propertys = property.split(":");
//        Arrays.asList(propertys).stream().forEach(System.out::println);
        MyClassLoader myClassLoader = new MyClassLoader("ClassLoader\\src\\main\\java\\ClassLoaderTest\\MyClass.class");
        Object myClass =  myClassLoader.loadClass("ClassLoaderTest.MyClass").newInstance();
        System.out.println(myClass.getClass());
        System.out.println("The class loader used now is : " + myClass.getClass().getClassLoader().getName());
        System.out.println(myClass.getClass().getClassLoader().getParent().getName());
        System.out.println(Child.childNum);
        File file = new File("ClassLoader\\src\\main\\java\\ClassLoaderTest\\MyClass.java");
        if (file.exists()) {
            System.out.println("exists!");
        } else {
            System.out.println("not exists!");
        }

//        A a = new A();
//        A b = new A();
//        new Thread(() -> {
//            a.method1();
//        }).start();
//
//        new Thread(() -> {
//            b.method2();
//        }).start();
    }
}

package ClassLoaderTest;

import java.io.File;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader("ClassLoader\\src\\main\\java\\ClassLoaderTest\\MyClass.class");
        Object myClass =  myClassLoader.loadClass("ClassLoaderTest.MyClass").newInstance();
        System.out.println(myClass.getClass());
        System.out.println(myClass.getClass().getClassLoader().getName());
        System.out.println(myClass.getClass().getClassLoader().getParent().getName());
//        System.out.println(Child.childNum);
//        File file = new File("ClassLoader\\src\\main\\java\\ClassLoaderTest\\MyClass.java");
//        if (file.exists()) {
//            System.out.println("exists!");
//        } else {
//            System.out.println("not exists!");
//        }
    }
}

package ClassLoaderTest;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader("mihClassLoader");
        Object myClass =  myClassLoader.loadClass("ClassLoaderTest.MyClass").newInstance();
        System.out.println(myClass.getClass());
        System.out.println(myClass.getClass().getClassLoader().getName());
        System.out.println(myClass.getClass().getClassLoader().getParent().getName());
    }
}

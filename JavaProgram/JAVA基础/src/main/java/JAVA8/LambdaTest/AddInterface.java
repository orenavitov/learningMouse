package JAVA8.LambdaTest;

/**
 * 如果一个接口只有一个抽象方法， 则这个接口可以视为函数式接口（FunctionInterface）, 接口中的默认实现方法不算；
 * Lambda表达式实际上就是一个函数式接口；
 * 如果一个接口有@FunctionalInteraface的生命， 则这个接口中只能有一个抽象方法， 如果有其他抽象方法， 会报错；
 */
@FunctionalInterface
public interface AddInterface {

    String add(String a, String b);

    default int add(int a, int b) {
        return a + b;
    }
}

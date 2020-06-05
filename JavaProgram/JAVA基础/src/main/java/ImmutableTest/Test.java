package ImmutableTest;

import java.util.stream.IntStream;

/**
 * 不可变对象：
 * 1. 不要提供setter方法
 * 2. 所有的成员变量都是private final
 * 3. 不允许其他类继承
 * 4. 成员变量如果有引用类型， 那这些引用的类型也不能修改， 返回这些引用时， 返回一个克隆实例；
 * 不可变对象一定是线程安全的； 可变对象也不一定是不安全的；
 * String 就是一个不可变对象；
 * servlet 不是线程安全的；
 */
public final class Test {

    public static void main(String[] args) {
        Person person = new Person("mih", "hb");
        IntStream.range(0, 9).forEach(i -> {
            new UsePersonThread(person).start();
        });
    }
}

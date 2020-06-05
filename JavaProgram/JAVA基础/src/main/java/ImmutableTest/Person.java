package ImmutableTest;

/**
 * 这个对象是线程安全的：
 * 1. 没有提供改变name, address的方法；
 * 2. name, address 都是final类型的；
 */
public class Person {
    private final String name;
    private final String address;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.address;
    }
}

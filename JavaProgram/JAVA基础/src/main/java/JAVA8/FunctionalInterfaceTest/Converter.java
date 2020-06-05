package JAVA8.FunctionalInterfaceTest;
@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}

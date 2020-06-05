package JAVA8.FunctionalInterfaceTest;

public class Test {
    public static void main(String[] args) {
        Converter<String, Integer> converter = Integer :: valueOf;
        int result = converter.convert("123");
    }
}

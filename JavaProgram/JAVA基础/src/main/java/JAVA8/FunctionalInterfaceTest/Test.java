package JAVA8.FunctionalInterfaceTest;

import java.util.function.Function;

public class Test {
    public static void main(String[] args) {
        // Converter
        Converter<String, Integer> converter = Integer :: valueOf;
        int result = converter.convert("123");
        //Function
        Function<Integer, String> function = String :: valueOf;
        String functionTestResult = function.apply(2);
        System.out.println("result : " + functionTestResult);
    }
}

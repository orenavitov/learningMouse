package ThreadContextTest;

import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        IntStream.range(1, 5).forEach(i -> {
           new Thread(new Execution()).start();
        });
    }
}

package ThreadPerMessageTest;

import java.util.stream.IntStream;

/**
 * 针对每个Message安排一个单独的线程进行处理
 */
public class Test {
    public static void main(String[] args) {
        MessageHandler messageHandler = new MessageHandler();
        IntStream.rangeClosed(1, 10).forEach(i -> {
            messageHandler.request(new Message(String.valueOf(i)));
        });
        messageHandler.finish();
    }
}

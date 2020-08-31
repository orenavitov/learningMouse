package BlockQue.ArrayBlockingQue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * ArrayBlockingQueue 在构建是需要输入que的容量大小
 *
 */
public class Example1 {

    public static void main(String[] args) {

        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        /**
         * ArrayBlockingQueue是天然的生产者消费者模式， 如果que满了需要等待消费；
         * put(), 在队列的尾部添加元素, 队列满了会阻塞
         * take(), 在队列呃头部取元素， 队列空了会阻塞
         * add()， 在队列的尾部添加元素， 队列满了不会阻塞， 会抛出一个异常
         * clear(), 移除所有元素， 队列是空的也不会阻塞
         * offer(), 和add()相同， 就是不会抛出异常
         * peek(), 取队首的元素，不会移除该元素 队列为空返回null， 不会阻塞；
         * poll(), 取队首的元素，会移除该元素 队列为空返回null， 不会阻塞；
         */
        try {
            blockingQueue.put("hello");
            blockingQueue.put("mi");
            blockingQueue.put("hao");



//            blockingQueue.add(" ！");
//            blockingQueue.clear();
//            blockingQueue.offer("!");
//            String item = blockingQueue.peek();
            String item = blockingQueue.poll();
            System.out.println(item);
            System.out.println(blockingQueue.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

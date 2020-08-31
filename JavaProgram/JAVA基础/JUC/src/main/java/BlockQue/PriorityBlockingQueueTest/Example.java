package BlockQue.PriorityBlockingQueueTest;

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.IntStream;

/**
 * PriorityBlockingQueue 带排序的blockQue 不用声明容量， 默认是11, 超过默认容量还可以继续添加， 上线是Integer.MAX
 * 不允许添加null, 不允许添加没有实现Comparable的元素类型(除非在构建PriorityBlockingQueue时传入, Comparator)
 * 由于没有容量限制， add() = offer() = put()
 */
public class Example {
    public static void main(String[] args) {
        PriorityBlockingQueue<Integer> blockingQueue = new PriorityBlockingQueue<>(10, (t1, t2) -> {
            return -(t1 - t2);
        });

        IntStream.rangeClosed(1, 20).forEach(i -> {
            blockingQueue.put(i);
        });

        Iterator<Integer> iterator = blockingQueue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}

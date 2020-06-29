package FutureTest;

/**
 * Future模式：
 * 方法A， 使用方法B， 方法B耗时很长， 串行执行的话， 方法A需要等待方法B执行完成； Future模式中， 方法A使用方法B
 * 方法B会立刻返回一个Future， 但方法B不一定执行完成， 过一段时间后方法A可以凭借这个Future查看方法B是否完成；
 * 思路：
 * 最重要的是AsynFuture, 这个类要完成两个任务： 1. 在B没有完成时阻塞A; 2. 在B完成时通知A；
 * FutureService的任务是其中一个线程执行任务， 并在任务执行完成后告诉AsynFuture;
 * 这样还会存在一个问题， 假设A调用B之后去做其他事情， 但A很快也吧其他事情做完了， 但这时B还没有执行完， 这时A使用AsynFuture的get,
 * 又会陷入等待；
 * 解决办法： 使用callback， A不要再调用AsynFuture的get了， 让B执行完后主动通知A；
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        FutureService futureService = new FutureService();
        Future<String> future = futureService.submit(() -> {
            try {
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISH";
        }, System.out :: println);

        System.out.println("Do other things for 1s");
        Thread.sleep(1_000);
        System.out.println(future.get());
    }
}

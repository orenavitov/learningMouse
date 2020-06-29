package ActiveObjectsTest;

/**
 * ActionObject 两个方法： 一个makeString 产生字符串， 一个displayString展示字符串；
 * ActionObject 两个实现Servent, ActionObjectProxy, 实际工作的是Servent， 但Servent不能直接使用， 要通过使用ActionObjectPorxy;
 * Servent和ActionObjectProxy都不对外直接使用， 通过ActionObjectFactory获得一个ActionObjectProxy;
 * 因此ActionObjectFactory的构造方法中要完成的任务有：
 * 1. 构造一个Servent;
 * 2. 构造一个SchedulerThread由于执行Servent， 但SchedulerThread并不直接执行Servent， 而是通过invoke一个Request， 通过执行Request
 *    中的execute方法来执行Servent中的内容， 这样做可以将Servent中要做的事封装成Request放进一个执行队列中， 采用Futer的方法；
 */
public class Test {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread(activeObject, "M").start();
        new MakerClientThread(activeObject, "H").start();

        new DisplayClientThread("A", activeObject).start();
    }
}

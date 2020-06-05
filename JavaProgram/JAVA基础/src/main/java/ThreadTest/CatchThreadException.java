package ThreadTest;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 线程在执行过程中如果出错， JVM是不能在外部进行捕获的， 只能在线程内进行捕获， 这样在程序开始运行时只能通过日志的方式进行跟踪
 *
 */
public class CatchThreadException {

    public static void main(String[] args) {
        int a = 1;
        int b = 0;
        Thread t = new Thread("T") {

            @Override
            public void run() {
                int result = a / b;
            }
        };
        t.start();
        t.setUncaughtExceptionHandler((thread, exception) ->{
            Arrays.asList(thread.getStackTrace()).stream().filter(e -> !e.isNativeMethod())
                    .forEach(e -> {
                        System.out.println(Thread.currentThread().getName() + ":" +e.getClassName() + ":" + e.getMethodName() + ":" + e.getLineNumber());
                    });
//            System.out.println(thread.getName() + ":" +exception);
        });
    }

}

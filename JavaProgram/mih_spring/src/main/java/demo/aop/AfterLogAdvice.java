package demo.aop;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * Created by mihao on 2018/4/1.
 */
public class AfterLogAdvice implements AfterReturningAdvice {

    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        String targetClassName = o1.getClass().getName();

        String targetMethodName = method.getName();

        String logInfo = "后置通知：" + targetClassName + "类的" + targetMethodName + "方法已经执行";

        System.out.println(logInfo);
    }
}

package demo.aop;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by mihao on 2018/3/31.
 */
public class BeforeLogAdvice implements MethodBeforeAdvice {

    public void before(Method method, Object[] objects, Object o) throws Throwable {
        String targetClassName = o.getClass().getName();

        String targetMethodName = method.getName();

        String logInfoText = "前置通知：" + targetClassName + "类的" + targetMethodName + "方法开始执行";

        System.out.println(logInfoText);
    }
}

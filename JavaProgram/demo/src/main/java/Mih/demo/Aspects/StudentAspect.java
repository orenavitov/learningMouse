package Mih.demo.Aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StudentAspect {
    @Pointcut("execution(public * Mih.demo.Controllers.RestfulTest.hello(..)))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void beforProcess(){
        System.out.println("before!");
    }

    @After("pointCut()")
    public void afterProcess() {
        System.out.println("after!");
    }

}

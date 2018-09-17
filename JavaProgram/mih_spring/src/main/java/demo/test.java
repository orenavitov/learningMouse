package demo;

import demo.dao.impl.UserBizImpl;
import demo.dao.impl.UserBizImplByAnnotation;
import demo.dao.service.UserBiz;
import demo.model.Say;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by mihao on 2018/3/25.
 */
public class test {

    public static void main(String arags[]) {
        System.out.println("spring test");
        /**
         * beenFactory的实例：
         * 1. ClassPathXmlApplicationContext, 根据classpath路径找been xml配置文件
         * 2. FileSystemXmlApplicationContext, 根据文件路径找been xml配置文件
         * 3. XmlWebApplicatonContext, 从web应用中找been xml配置文件
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        UserBiz userBiz = (UserBiz) context.getBean("ub");
        boolean result = userBiz.login("admi", "123");
        System.out.println("result:" + result);
    }
}

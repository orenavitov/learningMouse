package mih;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.RedisClientTemplate;

public class test {
    public static void main(String[] args) {
        ApplicationContext ac =  new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
        RedisClientTemplate redisClient = (RedisClientTemplate)ac.getBean("redisClientTemplate");
        redisClient.set("a", "abc");
        System.out.println(redisClient.get("a"));
    }
}

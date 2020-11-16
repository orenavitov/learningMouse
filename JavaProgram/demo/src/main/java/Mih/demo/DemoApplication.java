package Mih.demo;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@SpringBootApplication
//@EnableCaching
public class DemoApplication {

	public static void main(String[] args) {
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
		ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
//		applicationContext.close();

	}
}

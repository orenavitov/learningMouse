package Mih.demo;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
		SpringApplication.run(DemoApplication.class, args);
	}

}

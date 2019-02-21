import config.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {
        "control", "config", "dao", "service", "springfox.documentation.schema"})
public class Example {


    public static void main(String[] args) throws Exception {
//        SpringApplication.run(Example.class, args);
        for (int i = 0; i < 5; i ++) {
            double temp = Math.random();
            long result = Math.round(temp);
            if (result == 0) {
                System.out.println("no");
            } if (result == 1) {
                System.out.println("yes");
            }
        }

    }
}

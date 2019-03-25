import config.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import service.SpringApplicationStartingListener;
import service.SpringStartedListener;
import service.SpringStoppedListener;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {
        "control", "config", "dao", "service", "springfox.documentation.schema"})
public class Example {
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Example.class);
        app.addListeners(new SpringApplicationStartingListener(), new SpringStartedListener(), new SpringStoppedListener());
        app.run();

    }
}

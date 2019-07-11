import config.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import service.SpringApplicationStartingListener;
import service.SpringStartedListener;
import service.SpringStoppedListener;
import service.impl.EmployeesService;
import service.impl.SalariesService;
import service.impl.SaySomething;
import service.impl.StoreService;
import service.service.EmployeesServiceImp;
import service.service.SalariesServiceImp;
import service.service.Say;
import service.service.StoreServiceImp;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {
        "control"})
public class Example {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Example.class);
        app.run();

    }
}

import control.EmployeesServiceApi;
import control.SayHello;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.impl.EmployeesService;
import service.impl.SalariesService;
import service.impl.SaySomething;
import service.impl.StoreService;
import service.service.EmployeesServiceImp;
import service.service.SalariesServiceImp;
import service.service.Say;
import service.service.StoreServiceImp;

@Configuration
public class GetBean {

//    @Bean
//    SayHello getSayHello() {
//        return new SayHello();
//    }
//
//    @Bean
//    EmployeesServiceApi getEmployeesServiceApi() {
//        return new EmployeesServiceApi();
//    }

    @Bean
    Say getSay() {
        return new SaySomething();
    }

    @Bean
    StoreService getStoreService() {
        return new StoreServiceImp();
    }

    @Bean
    EmployeesService getEmployeesService() {
        return new EmployeesServiceImp();
    }

    @Bean
    SalariesService getSalariesService() {
        return new SalariesServiceImp();
    }
}

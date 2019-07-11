package control;

import model.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.impl.EmployeesService;

@RestController
@RequestMapping("/employees")
public class EmployeesServiceApi {

    @Autowired
    private EmployeesService employeesService;

    @RequestMapping(value = "/createemployees", method = RequestMethod.POST)
    boolean createEmployees(Employees employees) {
        employeesService.createEmployees(employees);
        return true;
    }
}

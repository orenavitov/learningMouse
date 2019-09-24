package service.service;

import model.Employees;
import service.impl.EmployeesService;

import java.util.HashMap;
import java.util.Map;

public class EmployeesServiceImp implements EmployeesService {

    private static Map<Integer, Employees> employeesMap = new HashMap<>();

    @Override
    public boolean createEmployees(Employees employees) {
        try {
            int empNo = employees.getEmpNo();
            employeesMap.putIfAbsent(empNo, employees);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modifyEmployees() {
        return false;
    }
}

package service.service;

import model.Employees;
import model.Salaries;
import service.impl.SalariesService;

import java.util.HashMap;
import java.util.Map;

public class SalariesServiceImp implements SalariesService {

    private static Map<Integer, Salaries> salariesMap = new HashMap<>();


    @Override
    public boolean createSalaries(Employees employees) {
        return false;
    }

    @Override
    public Salaries getSalaries(int empNo) {
        return null;
    }

    @Override
    public boolean deleteSalaries(Employees employees) {
        return false;
    }
}

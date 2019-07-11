package service.impl;

import model.Employees;
import model.Salaries;

public interface SalariesService {

    boolean createSalaries(Employees employees);

    Salaries getSalaries(int empNo);

    boolean deleteSalaries(Employees employees);
}

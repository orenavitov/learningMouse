package model;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工薪水信息
 */
public class Salaries implements Serializable {

    public Salaries() {}

    //员工编号
    private int empNo;

    //员工薪水
    private int salary;

    //开始时间
    private Date fromDate;

    //结束时间
    private Date toDate;

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Salaries) {
            obj = (Salaries) obj;
            if (this.empNo == ((Salaries) obj).getEmpNo() &&
                    this.fromDate.getTime() == ((Salaries) obj).getFromDate().getTime()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

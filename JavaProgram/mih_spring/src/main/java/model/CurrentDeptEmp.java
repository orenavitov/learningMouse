package model;

import java.util.Date;

/**
 * 员工所在部门
 */
public class CurrentDeptEmp {

    public CurrentDeptEmp() {}

    //员工编号
    private int empNo;

    //部门编号
    private String deptNo;

    //员工所在该部门的开始时间
    private Date fromDate;

    //员工离开该部门的时间
    private Date toDate;

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
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
}

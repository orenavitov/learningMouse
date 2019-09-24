package model;

import java.io.Serializable;
import java.util.Date;

public class DeptManager implements Serializable {

    public DeptManager() {}

    private int empNo;

    private String deptNo;

    private Date fromDate;

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
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DeptManager) {
            obj =(DeptManager) obj;
            if (this.empNo == ((DeptManager) obj).getEmpNo() && this.deptNo.equals(((DeptManager) obj).getDeptNo())) {
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

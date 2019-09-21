package model;

import java.io.Serializable;
import java.util.Date;

public class DeptEmpLatestData implements Serializable {

    public DeptEmpLatestData() {}

    private int empNo;

    private Date fromDate;

    private Date toDate;

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
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
        if (obj instanceof DeptEmpLatestData) {
            obj =(DeptEmpLatestData) obj;
            if (this.empNo == ((DeptEmpLatestData) obj).getEmpNo() &&
                this.fromDate.getTime() == ((DeptEmpLatestData) obj).getFromDate().getTime() &&
                this.toDate.getTime() == ((DeptEmpLatestData) obj).getToDate().getTime()) {
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

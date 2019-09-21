package model;

import java.io.Serializable;
import java.util.Date;

public class Titles implements Serializable {

    public Titles() {
    }

    private int empNo;

    private String title;

    private Date fromDate;

    private Date toDate;

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        if (obj instanceof Titles) {
            obj = (Titles) obj;
            if (this.empNo == ((Titles) obj).getEmpNo() && this.title.equals(((Titles) obj).getTitle())
                    && this.fromDate.getTime() == ((Titles) obj).getFromDate().getTime()) {
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

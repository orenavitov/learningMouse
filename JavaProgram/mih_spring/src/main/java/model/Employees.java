package model;

import java.util.Date;

/**
 * 员工信息
 */
public class Employees {

    public Employees() {}

    //员工编号
    private int empNo;

    //员工生日
    private Date birthDate;

    //员工姓氏
    private String firstName;

    //员工名字
    private String lastName;

    //员工进入公司时间
    private Date hireDate;

    //员工性别
    private Gender gender;

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

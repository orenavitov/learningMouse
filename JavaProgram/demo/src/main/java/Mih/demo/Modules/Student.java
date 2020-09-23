package Mih.demo.Modules;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.util.Date;

public class Student {

    private String studentId;

    private String name;

    private Date birthday;

    private String sex;

    public Student() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "number: " + studentId + "\n" +
                "name: " + name + "\n" +
                "birthday: " + birthday + "\n" +
                "sex: " + sex;
    }
}

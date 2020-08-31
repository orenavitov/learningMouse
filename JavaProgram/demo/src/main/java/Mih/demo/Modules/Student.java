package Mih.demo.Modules;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
@Component
@Alias("Student")
public class Student {

    private String number;

    private String name;

    private String birthday;

    private String sex;

    public Student() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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
        return "number: " + number + "\n" +
                "name: " + name + "\n" +
                "birthday: " + birthday + "\n" +
                "sex: " + sex;
    }
}

package Mih.demo.Modules;

import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.Date;

public class Student implements InitializingBean, DisposableBean, Serializable {

    private int studentId;

    private String name;

    private Date birthday;

    private String sex;

    private String telephoneNumber;

    private String e_mailAddress;

    private String address;

    public Student() {
        System.out.println("执行构造方法.");
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
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

    @PostConstruct
    public void testPostConstruct() {
        System.out.println("执行 @PostConstruct 方法.");
    }

    @PreDestroy
    public void testPreDestory() {
        System.out.println("执行 @PreDestroy 方法.");
    }



    @Override
    public void destroy() throws Exception {
        System.out.println("执行 DisposableBean 中的 destory().");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行InitializingBean 中的 afterPropertiesSet().");
    }

    public void myInit() {
        System.out.println("执行指定的 init().");
    }

    public void myDestroy() {
        System.out.println("执行指定的 destroy().");
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getE_mailAddress() {
        return e_mailAddress;
    }

    public void setE_mailAddress(String e_mailAddress) {
        this.e_mailAddress = e_mailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

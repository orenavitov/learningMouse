package demo.model;

import java.io.Serializable;

public class Persion implements Serializable{
    private String name;
    private int age;

    public void Persion() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

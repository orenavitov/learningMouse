package Mih.demo.Modules;

import java.io.Serializable;

public class Course implements Serializable {
    private int classId;
    private int teacherId;
    private String className;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Course() {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "classId=" + classId +
                ", teacherId=" + teacherId +
                ", className='" + className + '\'' +
                '}';
    }
}

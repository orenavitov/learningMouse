package Mih.demo.Modules;

import org.springframework.stereotype.Component;


@Component
public class Score {
    private String studentId;
    private String classId;
    private int score;

    public Score() {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "studentId=" + studentId +
                ", classId=" + classId +
                ", score=" + score +
                '}';
    }
}

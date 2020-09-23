package Mih.demo.Modules;

import org.springframework.stereotype.Component;


@Component
public class Score {
    private int studentId;
    private int classId;
    private int score;

    public Score() {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
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

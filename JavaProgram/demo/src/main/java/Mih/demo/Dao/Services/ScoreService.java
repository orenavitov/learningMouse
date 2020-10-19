package Mih.demo.Dao.Services;

public interface ScoreService {
    int getScoreByStudentId(String studentId, String classId);

    void updateScoreByStudentId(String studentId, String classId, int score);
}

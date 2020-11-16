package Mih.demo.Dao.Services;

import Mih.demo.Modules.Score;

import java.util.List;

public interface ScoreService {
    List<Score> getScoreByStudentId(String studentId);

    List<Score> getScoreByClassId(String classId);

    Score getScoreByStudentIdAndClassId(String studentId, String classId);

    void updateScoreByStudentId(String studentId, String classId, int score);


}

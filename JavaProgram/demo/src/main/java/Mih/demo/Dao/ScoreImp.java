package Mih.demo.Dao;

import Mih.demo.Mappers.ScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ScoreImp implements ScoreService {

    @Autowired
    ScoreMapper scoreMapper;

    @Override
    @Cacheable(value = "score")
    public int getScoreByStudentId(String studentId, String classId) {
        return scoreMapper.getScoreByStudentId(studentId, classId);
    }

    @Override
    public void updateScoreByStudentId(String studentId, String classId, int score) {
        scoreMapper.updateScoreByStudentId(studentId, classId, score);
    }
}

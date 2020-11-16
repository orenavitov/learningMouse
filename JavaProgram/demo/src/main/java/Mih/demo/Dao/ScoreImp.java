package Mih.demo.Dao;
import Mih.demo.Dao.Services.ScoreService;
import Mih.demo.Mappers.ScoreMapper;
import Mih.demo.Modules.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScoreImp implements ScoreService {

    @Autowired
    ScoreMapper scoreMapper;

    @Override
    public List<Score> getScoreByStudentId(String studentId) {
        return scoreMapper.getScoreByStudentId(studentId);
    }

    @Override
    public List<Score> getScoreByClassId(String classId) {
        return scoreMapper.getScoreByClassId(classId);
    }

    @Override
    @Cacheable(value = "score", key = "#studentId + '_' + #classId")
    public Score getScoreByStudentIdAndClassId(String studentId, String classId) {
        return scoreMapper.getScoreByStudentIdAndClassId(studentId, classId);
    }

    @Override
    @CacheEvict(value = "score", key = "#studentId + '_' + #classId")
    public void updateScoreByStudentId(String studentId, String classId, int score) {
        scoreMapper.updateScoreByStudentId(studentId, classId, score);
    }
}

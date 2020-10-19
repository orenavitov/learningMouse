package Mih.demo.Dao;
import Mih.demo.Dao.Services.ScoreService;
import Mih.demo.Mappers.ScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ScoreImp implements ScoreService {

    @Autowired
    ScoreMapper scoreMapper;

    @Override
    @Cacheable(value = "score", key = "#studentId + '_' + #classId")
    public int getScoreByStudentId(String studentId, String classId) {
        int score = scoreMapper.getScoreByStudentId(studentId, classId);
        return score;
    }

    @Override
    @CacheEvict(value = "score", key = "#studentId + '_' + #classId")
    public void updateScoreByStudentId(String studentId, String classId, int score) {
        scoreMapper.updateScoreByStudentId(studentId, classId, score);
    }
}

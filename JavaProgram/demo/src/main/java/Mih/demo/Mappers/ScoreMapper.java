package Mih.demo.Mappers;

import Mih.demo.Modules.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScoreMapper {

    List<Score> getScoreByStudentId(@Param("SId") String studentId);

    Score getScoreByStudentIdAndClassId(@Param("SId") String studentId, @Param("CId") String classId);



    void updateScoreByStudentId(@Param("SId") String studentId,
                                @Param("CId") String classId,
                                @Param("score") int score);

    List<Score> getScoreByClassId(@Param("CId") String classId);
}

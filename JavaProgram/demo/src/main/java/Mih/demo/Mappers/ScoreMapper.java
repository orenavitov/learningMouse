package Mih.demo.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScoreMapper {

    int getScoreByStudentId(@Param("SId") String studentId, @Param("CId") String classId);

    void updateScoreByStudentId(@Param("SId") String studentId,
                                @Param("CId") String classId,
                                @Param("score") int score);
}

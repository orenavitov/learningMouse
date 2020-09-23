package Mih.demo.Mappers;

import Mih.demo.Modules.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TeacherMapper {

    Teacher getTeacherById(@Param("TId") String teacherId);

    List<Teacher> getAllTeachers();
}

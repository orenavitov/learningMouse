package Mih.demo.Mappers;

import Mih.demo.Modules.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {

    List<Course> getCoursesByTeacherId(@Param("TId") String teacherId);

    void selectCourse(@Param("CId") String classId,
                      @Param("Cname") String className,
                      @Param("TId") String teacherId);
}

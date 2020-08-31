package Mih.demo.Mappers;

import Mih.demo.Modules.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {
//    @Select("select a.* from student as a where a.s_id=#{s_id}")
    Student getStudentByNumber(@Param("s_id") String number);
}

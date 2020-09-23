package Mih.demo.Mappers;

import Mih.demo.Modules.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {

    Student getStudentByNumber(@Param("SId") String number);

    List<Student> getAllStudents();
}

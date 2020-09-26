package Mih.demo.Mappers;

import Mih.demo.Modules.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface StudentMapper {

    Student getStudentByNumber(@Param("SId") String number);

    List<Student> getAllStudents();

    void createStudent(@Param("SId") String number,
                       @Param("Sname") String name,
                       @Param("Sage") Date birthday,
                       @Param("Ssex") String sex);

    void createStudents(@Param("students") List<Student> students);

    void delStudentById(@Param("SId") String studentId);
}

package Mih.demo.Mappers;

import Mih.demo.Modules.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface TeacherMapper {

    Teacher getTeacherById(@Param("TId") String teacherId);

    List<Teacher> getAllTeachers();

    void createTeacher(@Param("TId") String number,
                       @Param("Tname") String name,
                       @Param("birthday") Date birthday,
                       @Param("sex") String sex,
                       @Param("e_mailAddress") String e_mailAddress,
                       @Param("telephoneNumber") String telephoneNumber,
                       @Param("address") String address);


}

package Mih.demo.Dao;

import Mih.demo.Dao.Services.TeacherService;
import Mih.demo.Mappers.TeacherMapper;
import Mih.demo.Modules.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherImp implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    @Cacheable(value = "teacher", key = "#teacherId")
    public Teacher findTeacherById(String teacherId) {
        return teacherMapper.getTeacherById(teacherId);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherMapper.getAllTeachers();
    }



    @Override
    public void createTeacher(Teacher teacher) {
        teacherMapper.createTeacher(
                teacher.getTelephoneNumber(),
                teacher.getName(),
                teacher.getBirthday(),
                teacher.getSex(),
                teacher.getTelephoneNumber(),
                teacher.getE_mailAddress(),
                teacher.getAddress());
    }


}

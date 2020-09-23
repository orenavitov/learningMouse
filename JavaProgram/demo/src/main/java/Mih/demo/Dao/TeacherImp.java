package Mih.demo.Dao;

import Mih.demo.Mappers.TeacherMapper;
import Mih.demo.Modules.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherImp implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher findTeacherById(String teacherId) {
        return teacherMapper.getTeacherById(teacherId);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherMapper.getAllTeachers();
    }
}

package Mih.demo.Dao.Services;

import Mih.demo.Modules.Annotation;
import Mih.demo.Modules.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher findTeacherById(String teacherId);

    List<Teacher> getAllTeachers();



    void createTeacher(Teacher teacher);
}

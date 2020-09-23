package Mih.demo.Dao;

import Mih.demo.Modules.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher findTeacherById(String teacherId);

    List<Teacher> getAllTeachers();
}

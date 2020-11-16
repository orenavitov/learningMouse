package Mih.demo.Dao.Services;

import Mih.demo.Modules.Course;

import java.util.List;

public interface ClassService {

    List<Course> getCoursesByTeacherId(String teacherId);

    void selectCourse(Course course);

}

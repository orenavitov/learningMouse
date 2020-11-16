package Mih.demo.Dao;

import Mih.demo.Dao.Services.ClassService;
import Mih.demo.Mappers.CourseMapper;
import Mih.demo.Modules.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ClassImp implements ClassService {

    @Autowired
    CourseMapper courseMapper;


    @Override
    public List<Course> getCoursesByTeacherId(String teacherId) {
        return courseMapper.getCoursesByTeacherId(teacherId);
    }

    @Override
    public void selectCourse(Course course) {
        int classId = course.getClassId();
        String className = course.getClassName();
        int teacherId = course.getTeacherId();
        courseMapper.selectCourse("" + classId, className, "" + teacherId);
    }
}

package Mih.demo.Dao;

import Mih.demo.Mappers.StudentMapper;
import Mih.demo.Modules.Student;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentImp implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    public Student findStudentByNumber(String number) {
        return studentMapper.getStudentByNumber(number);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    @Override
    public void createStudent(Student student) {
        studentMapper.createStudent(
                student.getStudentId(),
                student.getName(),
                student.getBirthday(),
                student.getSex());
    }

    @Override
    public void createStudents(List<Student> students) {
        studentMapper.createStudents(students);
    }

    @Override
    public void deleteStudentById(String studentId) {
        studentMapper.delStudentById(studentId);
    }


}

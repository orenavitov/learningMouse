package Mih.demo.Dao;

import Mih.demo.Mappers.StudentMapper;
import Mih.demo.Modules.Student;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentImp implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    public Student findStudentByNumber(String number) {
        return studentMapper.getStudentByNumber(number);
    }
}

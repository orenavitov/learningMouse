package Mih.demo.Dao;

import Mih.demo.CacheServer.RedisServer;
import Mih.demo.Dao.Services.StudentService;
import Mih.demo.Mappers.StudentMapper;
import Mih.demo.Modules.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentImp implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    RedisServer redisServer;

    @Autowired
    CacheManager cacheManager;

    /*
    对于查询操作首先从缓存中进行查询
     */
    @Cacheable(value = "student", keyGenerator = "simpleKeyGenerator", cacheManager = "cacheManager")
    public Student findStudentByNumber(String number) {
        Student student = studentMapper.getStudentByNumber(number);
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    /*
    对于创建操作， 直接修改数据库
     */
    @Override
    public void createStudent(Student student) {
        studentMapper.createStudent(
                student.getStudentId(),
                student.getName(),
                student.getBirthday(),
                student.getSex());
    }

    /*
    对于创建操作， 直接修改数据库
     */
    @Override
    public void createStudents(List<Student> students) {
        studentMapper.createStudents(students);
    }

    @Override
    @CacheEvict(value = "student", key = "#studentId")
    public void deleteStudentById(String studentId) {
        studentMapper.delStudentById(studentId);
    }


}

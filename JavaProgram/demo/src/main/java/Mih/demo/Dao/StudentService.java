package Mih.demo.Dao;

import Mih.demo.Modules.Student;

import java.util.List;

public interface StudentService {



    Student findStudentByNumber(String number);

    List<Student> getAllStudents();
}

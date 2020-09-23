package Mih.demo.Controllers;

import Mih.demo.Dao.StudentService;
import Mih.demo.Modules.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @RequestMapping("/getstudentbyid")
    @ResponseBody
    public String getStudentByNumber(@RequestParam("id")String number) {
        Student student = studentService.findStudentByNumber(number);
        String result = student.toString();
        return result;
    }

    @RequestMapping("/getallstudents")
    @ResponseBody
    public String getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        StringBuilder result = new StringBuilder();
        students.forEach(student -> {
            result.append(student.toString() + "/n");
        });
        return result.toString();
    }
}

package Mih.demo.Controllers;

import Mih.demo.Dao.StudentService;
import Mih.demo.Modules.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;

    @RequestMapping("/getstudentbynumber")
    @ResponseBody
    public String getStudentByNumber(@RequestParam("number")String number) {
        Student student = studentService.findStudentByNumber(number);
        String result = student.toString();
        return result;
    }
}

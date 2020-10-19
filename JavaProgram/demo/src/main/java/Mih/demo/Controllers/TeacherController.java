package Mih.demo.Controllers;

import Mih.demo.Dao.Services.TeacherService;
import Mih.demo.Modules.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @RequestMapping("/getteacherbyid")
    @ResponseBody
    public String getTeacherById(@RequestParam("id")String number) {
        Teacher teacher = teacherService.findTeacherById(number);
        String result = teacher.toString();
        return result;
    }

    @RequestMapping("/getallteachers")
    @ResponseBody
    public String getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        StringBuilder result = new StringBuilder();
        teachers.forEach(teacher -> {
            result.append(teacher.toString() + "\n");
        });
        return result.toString();
    }

}

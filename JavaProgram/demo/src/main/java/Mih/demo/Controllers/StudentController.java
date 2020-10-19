package Mih.demo.Controllers;

import Mih.demo.Dao.Services.ScoreService;
import Mih.demo.Dao.Services.StudentService;
import Mih.demo.Modules.Student;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    ScoreService scoreService;

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

    @RequestMapping(value = "/createstudent", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void createStudent(@RequestBody JSONObject jsonParam) {
        Student student = jsonParam.toJavaObject(Student.class);
        studentService.createStudent(student);
    }

    @RequestMapping(value = "/createstudents", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void createStudents(@RequestBody JSONArray jsonParams) {
        ArrayList<Student> students = (ArrayList<Student>)jsonParams.toJavaList(Student.class);
//        ArrayList<Student> students = (ArrayList<Student>)JSONArray.parseArray(jsonParam.toJSONString(), Student.class);
        studentService.createStudents(students);
    }

    @RequestMapping(value = "/deletestudentbyid", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteStudentById(@RequestParam("id")String number) {
        studentService.deleteStudentById(number);
    }

    @RequestMapping(value = "/getScoreByStudentId", method = RequestMethod.GET)
    @ResponseBody
    public int getScoreByStudentId(@RequestParam("sid")String studentId, @RequestParam("cid")String classId) {

        return scoreService.getScoreByStudentId(studentId, classId);

    }

    @RequestMapping(value = "/updateScoreByStudentId", method = RequestMethod.PUT)
    @ResponseBody
    public void updateScoreByStudentId(@RequestParam("sid")String studentId,
                                       @RequestParam("cid")String classId,
                                       @RequestParam("score") int score) {
        scoreService.updateScoreByStudentId(studentId, classId, score);

    }

}

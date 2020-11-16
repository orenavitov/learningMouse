package Mih.demo.Controllers;

import Mih.demo.Dao.Services.ClassService;
import Mih.demo.Dao.Services.ScoreService;
import Mih.demo.Dao.Services.StudentService;
import Mih.demo.Modules.Course;
import Mih.demo.Modules.Response;
import Mih.demo.Modules.Score;
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

    @Autowired
    ClassService classService;

    @RequestMapping("/getstudentbyid")
    @ResponseBody
    public Response getStudentByNumber(@RequestParam("id") String number) {
        Response response = new Response();
        try {
            Student student = studentService.findStudentByNumber(number);
            response.setObj(student);
            response.setState(200);
        } catch (Exception e) {
            response.setState(500);
            response.setMessage("error in find student by id!");
        }

        return response;
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
    public Response createStudent(@RequestBody JSONObject jsonParam) {
        Response response = new Response();
        try {
            Student student = jsonParam.toJavaObject(Student.class);
            studentService.createStudent(student);
            response.setState(200);
        } catch (Exception e) {
            response.setState(500);
        }
        return response;
    }

    @RequestMapping(value = "/createstudents", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void createStudents(@RequestBody JSONArray jsonParams) {
        ArrayList<Student> students = (ArrayList<Student>) jsonParams.toJavaList(Student.class);
        studentService.createStudents(students);
    }

    @RequestMapping(value = "/deletestudentbyid", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteStudentById(@RequestParam("id") String number) {
        studentService.deleteStudentById(number);
    }

    @RequestMapping(value = "/updatestudentbyid", method = RequestMethod.PUT)
    @ResponseBody
    public void updateStudent(@RequestParam("id") String number, @RequestBody JSONArray jsonParam) {
        Student student = jsonParam.toJavaObject(Student.class);
        studentService.updateStudentById(student);
    }

    @RequestMapping(value = "/selectcourse", method = RequestMethod.POST)
    @ResponseBody
    public Response selectCourse(@RequestBody JSONObject jsonParam) {
        Course course = (Course) jsonParam.toJavaObject(Course.class);
        classService.selectCourse(course);
        Response response = new Response();
        return response;
    }

}

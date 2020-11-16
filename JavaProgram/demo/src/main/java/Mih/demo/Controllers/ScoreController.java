package Mih.demo.Controllers;

import Mih.demo.Dao.Services.ScoreService;
import Mih.demo.Modules.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @RequestMapping("/getscoresbyclassid")
    @ResponseBody
    List<Score> getScoresByClassId(@RequestParam("classId")String classId) {
        return scoreService.getScoreByClassId(classId);
    }

    @RequestMapping(value = "/getScoreByStudentIdAndClassId", method = RequestMethod.GET)
    @ResponseBody
    public Score getScoreByStudentIdAndClassId(@RequestParam("sid") String studentId, @RequestParam("cid") String classId) {
        return scoreService.getScoreByStudentIdAndClassId(studentId, classId);

    }

    @RequestMapping(value = "/getScoreByStudentId", method = RequestMethod.GET)
    @ResponseBody
    public List<Score> getScoreByStudentId(@RequestParam("sid") String studentId) {
        return scoreService.getScoreByStudentId(studentId);
    }

    @RequestMapping(value = "/updateScoreByStudentId", method = RequestMethod.PUT)
    @ResponseBody
    public void updateScoreByStudentId(@RequestParam("sid") String studentId,
                                       @RequestParam("cid") String classId,
                                       @RequestParam("score") int score) {
        scoreService.updateScoreByStudentId(studentId, classId, score);

    }
}

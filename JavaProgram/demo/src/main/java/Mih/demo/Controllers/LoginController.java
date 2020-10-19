package Mih.demo.Controllers;

import Mih.demo.Dao.Response;
import Mih.demo.Modules.Student;
import Mih.demo.Modules.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public User test() {
        User user = new User();
        user.setPassWord("mihao0804");
        user.setUserName("mihao");
        return user;
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Response login(@RequestBody JSONObject jsonParam) {
        User user = jsonParam.toJavaObject(User.class);
        return new Response();
    }
}

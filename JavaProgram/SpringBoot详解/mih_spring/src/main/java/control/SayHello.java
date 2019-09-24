package control;

import dao.imp.DepartmentServiceApi;
import dao.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import model.Departments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.service.Say;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mih")
@Api(value = "mih restful", description = "test", produces = MediaType.APPLICATION_JSON_VALUE)
public class SayHello {

    @Autowired
    private Say say;

    @ApiOperation(value = "create a user", notes = "input a user object")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "User", required = true, dataType = "User")
    })
    @RequestMapping(value = "/sayhello", method = RequestMethod.GET)
    @ResponseBody
    String sayHello() {
        say.sayHello();
        return "hello!";

    }

}

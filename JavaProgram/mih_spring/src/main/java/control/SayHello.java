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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mih")
@Api(value = "mih restful", description = "test", produces = MediaType.APPLICATION_JSON_VALUE)
public class SayHello {

    @Autowired
    private DepartmentService departmentApi;

    @ApiOperation(value = "create a user", notes = "input a user object")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "User", required = true, dataType = "User")
    })
    @RequestMapping(value = "/getdepartments", method = RequestMethod.GET)
    @ResponseBody
    List<Departments> getDepartments() {
        System.out.println("Get Departments.");
        List<Departments> departments = departmentApi.getDepartemnts();

        return departments;
    }

}

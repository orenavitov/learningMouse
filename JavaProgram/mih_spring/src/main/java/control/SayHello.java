package control;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.Connection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import model.Form;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mih")
@Api(value = "mih restful", description = "test", produces = MediaType.APPLICATION_JSON_VALUE)
public class SayHello {

    @Autowired
    private Connection connection;

    @ApiOperation(value = "test", notes = "just a test!")
    @RequestMapping(value = "/sayhello", method = RequestMethod.GET)
    String sayHello() {
        log.info("hello! everyone!");
        return "hello world";

    }

    @ApiOperation(value = "create a user", notes = "input a user object")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "User", required = true, dataType = "User")
    })
    @RequestMapping(value = "/createuser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    String createUser(User user, BindingResult bindingResult) {
        System.out.println("see there");
        return "ok";
    }

    @RequestMapping(value = "/createform", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody Form createForm(@RequestBody Form form) {

        return form;
    }

}

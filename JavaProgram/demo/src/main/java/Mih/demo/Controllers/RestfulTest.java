package Mih.demo.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestfulTest {

    Logger logger = LoggerFactory.getLogger(RestfulTest.class);

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        logger.debug("Request in /hello");
        return "hello World";
    }


}

package demo.controller;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import demo.model.Persion;

public class HelloController extends Controller{

    //访问的唯一路径，会把上下文路径覆盖
    @ActionKey("i")
    public void index() {

        renderText("index");
    }

    public void sayHello() {
        renderText("hello");
    }

    @ActionKey("handle")
    public void handle() {
        String param1 = getPara(0);
        String param2 = getPara(1);
        renderText(param1 + " " + param2);
    }
}

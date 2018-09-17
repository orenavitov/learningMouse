package demo;
import com.jfinal.config.*;
import demo.controller.HelloController;

public class DemoConfig extends JFinalConfig {

    public void configConstant(Constants constants) {
        constants.setDevMode(true);
        constants.setUrlParaSeparator("&");
    }

    public void configRoute(Routes routes) {
        routes.add("/hello", HelloController.class);
    }

    public void configPlugin(Plugins plugins) {

    }

    public void configInterceptor(Interceptors interceptors) {
    }

    public void configHandler(Handlers handlers) {

    }
}

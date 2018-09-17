package rest;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by 米昊 on 2017/9/28.
 */
public class ApplicationApi extends ResourceConfig {
    public ApplicationApi() {
        register(SayHello.class);
    }
}

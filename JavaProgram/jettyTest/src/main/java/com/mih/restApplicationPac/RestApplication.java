package com.mih.restApplicationPac;

import com.mih.restPack.GoodBye;
import org.glassfish.jersey.server.ResourceConfig;


public class RestApplication extends ResourceConfig {

    public RestApplication() {
        register(GoodBye.class);
    }
}

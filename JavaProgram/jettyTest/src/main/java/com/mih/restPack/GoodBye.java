package com.mih.restPack;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/test")
public class GoodBye {

    @GET
    @Path("/sayGoodBye")
    public String say2GoodBye(@QueryParam("say") String string) {
        return "goodBye goodBye";
    }

    @GET
    @Path("/sayGoodBye")
    public String sayGoodBye() {
        return "goodBye";
    }


}

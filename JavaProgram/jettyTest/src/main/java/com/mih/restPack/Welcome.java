package com.mih.restPack;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("")
public class Welcome{
    @GET
    @Path("/sayWelcome")
    public String sayHello() {
        return "welcome";
    }
}

package com.mih.restPack;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("")
public class Hello {

    @GET
    @Path("/sayHello")
    public String sayHello() {
        return "Hello";
    }
}

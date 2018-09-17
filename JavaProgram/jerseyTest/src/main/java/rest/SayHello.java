package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Application;
/**
 * Created by 米昊 on 2017/9/27.
 */
@Path("/hello")
public class SayHello{
    @GET
    @Path("/sayHello")
    @Produces(MediaType.TEXT_PLAIN)
    public String say() {
        System.out.print("connectioned");
        return "hello";
    }

}

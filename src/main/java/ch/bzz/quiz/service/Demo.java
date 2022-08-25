package ch.bzz.quiz.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * test service
 */

@Path("demo")
public class Demo {

    @GET
    @Path("demo")
    @Produces(MediaType.TEXT_PLAIN)
    public Response demo(){
        return Response
                .status(200)
                .entity("Demo is Lunched")
                .build();
    }

}

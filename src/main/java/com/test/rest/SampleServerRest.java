package com.test.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class SampleServerRest extends ServerRest {

    public static void main(String[] args) throws Exception {
        SampleServerRest server = new SampleServerRest();
        server.start();
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return "Hello world";
    }
}

package com.ibm.jaxrs.sample;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.ibm.jaxrs.sample.dao.ResponseData01;


@Path("/helloworld02")
public class HelloWorldResource02 {


  @GET
  @Path("/sayHelloWorld02")
  @Produces("application/json")
  public Response sayHelloWorld02(@DefaultValue("XXX") @QueryParam("name1") final String name1,
      @DefaultValue("YYY") @QueryParam("name2") final String name2) {

    Response response = null;

    String strData = "Hello " + name1 + " " + name2;
    ResponseData01 responseData = new ResponseData01("OK", strData);

    response = Response.status(Response.Status.OK).entity(responseData).build();

    return response;

  }
}
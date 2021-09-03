package Sample.MavenLibertyRest01;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.ibm.json.java.JSONObject;

@Path("helloworldId")
public class HelloWorldResource01 {
	
	@GET
	@Path("/sayHelloWorld")
	public String sayHelloWorld() {
		return "HelloWorld";
	}
	
	@GET
	@Path("/getQueryParam")
	@Produces("application/json")
	public Response getQueryParam(@DefaultValue("XXX") @QueryParam("name1") final String name1,
			@DefaultValue("YYY") @QueryParam("name2") final String name2) {
		
		Response response = null;
		String strData = "Hello " + name1 + " " + name2;
		
		System.out.println(strData);
		
		response = Response.status(Response.Status.OK).entity(new JSONObject()).build();
		
		return response;
	}

}

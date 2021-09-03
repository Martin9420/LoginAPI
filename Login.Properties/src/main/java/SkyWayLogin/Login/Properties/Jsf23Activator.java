package SkyWayLogin.Login.Properties;


import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import EntityDTO.RequestDTO;
import EntityDTO.UserDTO;

/**This bean is required to activate JSF 2.3.
 * See https://github.com/eclipse-ee4j/mojarra#user-content-activating-cdi-in-jakarta-faces-30
 * 
 * Remove this class if you don't need JSF.
 */

@RequestScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("")
public class Jsf23Activator{
	
	@Inject
	LoginProcessMain loginlogic = new LoginProcessMain();
	
	@Inject
	LoginProcessUserSignIn loginUser = new LoginProcessUserSignIn();
	
	@Inject
	LoginProcessUserUpdate UpdateUser = new LoginProcessUserUpdate();
	
	@Inject
	LoginProcessUserDelete DeleteUser = new LoginProcessUserDelete();
	
	@Inject
	LoginGenerateJWT jwt = new LoginGenerateJWT();
	
	@Inject
	LogoutProcessUserSignOut logout = new LogoutProcessUserSignOut();
	
		@POST
		@Path("/UserJWT")
		public Response GetJWT(@BeanParam UserDTO body, RequestDTO request) {
			return jwt.service(body, request);
		}
		
		@POST
		@Path("/NewRegistUser")
		public Response PostRegist(@BeanParam UserDTO body, RequestDTO request) throws SQLException {
			return loginlogic.service(body, request);
		}
		
		@POST
		@Path("/LoginUser")
		public Response PostLogin(@BeanParam UserDTO body, RequestDTO request) throws Exception {
			return loginUser.service(body,  request);
		}
		
		@POST
		@Path("/LogoutUser")
		public Response PostLogout(@BeanParam UserDTO body, RequestDTO request) {
			return logout.service(body, request);
		}
		
		@PUT
		@Path("/UpdateUser")
		public Response UpdateUser(@BeanParam UserDTO body, RequestDTO request) throws Exception{
			return UpdateUser.service(body, request);
		}
		
		@DELETE
		@Path("/DeleteUser")
		public Response DeleteUser(@BeanParam UserDTO body, RequestDTO request) {
			return DeleteUser.service(body,  request);
		}
		
		
	}

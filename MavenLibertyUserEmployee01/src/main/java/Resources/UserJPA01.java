package Resources;

import static org.junit.Assert.assertFalse;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.digest.DigestUtils;

import com.ibm.jaxrs.User.Jaxrs.Entity.UserAccount;

@Path("/UserAccountJPA01")
public class UserJPA01 {
	
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserJPA01");
	EntityManager em = emf.createEntityManager();
	
	@GET
	@Path("/admin")
	@Produces("application/json")
	public Response getRecord() {
		
		List<UserAccount> accounts =
				em.createQuery("select a FROM UserAccount a", UserAccount.class).getResultList();
		
		Response response = Response.status(Response.Status.OK).entity(accounts).build();
		
		return response;
	}
	
	@POST
	@Path("/Account")
	@Produces("application/json")
	public Response insertRecord(
			@QueryParam("empNumber") final String empNumber,
			@QueryParam("Name") final String Name,
			@QueryParam("mailAdless") final String mailAdless,
			@QueryParam("Password") final String Password) {	
		
		UserAccount account = new UserAccount();
		account.setEmployeeNumber(empNumber);
		account.setName(Name);
		account.setmailAdless(mailAdless);
		account.setPassword(Password);
		
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();
		
		Response response = Response.status(Response.Status.OK).entity(account).build();
		
		return response;
	}
	
	@PUT
	@Path("/id")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateRecord(@QueryParam("id") final int Id,
			@QueryParam("name") final String Name,
			@QueryParam("empNumber") final String EmployeeNumber,
			@QueryParam("mailAdless") final String mailAdless,
			@QueryParam("Password") final String Password) {
		
		UserAccount account = new UserAccount();
		
		account.setId(Id);
		account.setName(Name);
		account.setEmployeeNumber(EmployeeNumber);
		account.setmailAdless(mailAdless);
		account.setPassword(Password);
		
		em.getTransaction().begin();
		em.find(UserAccount.class,Id);
		em.flush();
		em.getTransaction().commit();
		
		Response response = Response.status(Response.Status.OK).entity(account).build();
		
		return response;
	}
	
	@DELETE
	@Path("/id")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Transactional
	public Response deleteRecord(@QueryParam("id") final int id,
			@QueryParam("name") final String name, 
			@QueryParam("empNumber") final String empNumber,
			@QueryParam("mailAdless") final String mailAdless,
			@QueryParam("Password") final String Password) {
		
		UserAccount account = new UserAccount();
		
		account.setId(id);
		account.setName(name);
		account.setEmployeeNumber(empNumber);
		account.setmailAdless(mailAdless);
		account.setPassword(Password);
		
		em.getTransaction().begin();
		UserAccount UA = em.merge(account);
		em.remove(UA);
		em.flush();
		em.getTransaction().commit();
		
		Response response = Response.status(Response.Status.OK).entity(account).build();
		
		return response;
	}
	
	
}

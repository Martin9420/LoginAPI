package SkyWayLogin.Login.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import EntityDTO.RequestDTO;
import EntityDTO.UserDTO;
import ErrorResponse.ResponseEnumCommon;
import ResponseCommon.LogoutProcessUserSignout;
import util.StringUtil;

@RequestScoped
public class LogoutProcessUserSignOut {
	
	@Inject
	private StringUtil stringutil = new StringUtil();
	
	UserDTO body = new UserDTO();
	
	RequestDTO request = new RequestDTO();
	
	private static final String TAB = "\t";
	
	private static Logger log;
	
	public LogoutProcessUserSignOut() {
		log = LogManager.getLogger(LogoutProcessUserSignOut.this);
	}
	
	public Response GenerateResponse(ResponseEnumCommon responseenumcommon) {
		LogoutProcessUserSignout loginprocessusersignout = new LogoutProcessUserSignout();
		loginprocessusersignout.setTime(LocalDateTime.now().toString());
		loginprocessusersignout.setCode(responseenumcommon.getStatus());
		loginprocessusersignout.setMessage(responseenumcommon.getResponseMessage());
		return Response.status(responseenumcommon.getStatus()).entity(loginprocessusersignout).build();
	}
	
	public Response service(UserDTO body, RequestDTO request){
		LogoutProcessUserSignout loginprocessusersignout = new LogoutProcessUserSignout();
		ResponseEnumCommon responseenumcommon = ResponseEnumCommon.S200;
		
		Response TypeResponses = RequestBodyCheck(body, request);
		if(TypeResponses.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponses;
		}
		
		TypeResponses = ChangeUserActivityStatus(request);
		if(TypeResponses.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponses;
		}
		loginprocessusersignout.setCode(responseenumcommon.getStatus());
		loginprocessusersignout.setTime(LocalDateTime.now().toString());
		loginprocessusersignout.setMessage(responseenumcommon.getResponseMessage());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessusersignout).build();
	}
	
	private Response RequestBodyCheck(UserDTO body, RequestDTO request) {
		new LogoutProcessUserSignOut();
		LogoutProcessUserSignout loginprocessusersignout = new LogoutProcessUserSignout();
		//Method execution condition
		log.info("Method:RequestBodyCheck is Call for Service");
		log.info("Package:SkyWayLogin.Properties, Class:LogoutProcessUserSignOut, Method:RequestBodyCheck Start Time is" + LocalDateTime.now());
		if(stringutil.isNullorEmpty(request.getbitAdress())) {
				return GenerateResponse(ResponseEnumCommon.E400);
			}
		log.info("Package:SkyWayLogin.Properties, Class:LogoutProcessUserSignOut, Method:RequestBodyCheck Finish Time is" + LocalDateTime.now());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessusersignout).build();
	}

	private Response ChangeUserActivityStatus(RequestDTO request) {
		LogoutProcessUserSignout loginprocessusersignout = new LogoutProcessUserSignout();
		int Activity = 0;
		final String URL = "jdbc:postgresql://localhost:5432/postgres";
		final String USER = "postgres";
		final String PASSWORD = "password";
		final String SQL = "update mydb set useractivity = ? where bitAdress = ?";
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
			connection.setAutoCommit(false);
			try(PreparedStatement preparedstatement = connection.prepareStatement(SQL)){
				preparedstatement.setInt(1, Activity);
					preparedstatement.setString(2, request.getbitAdress());
						preparedstatement.executeUpdate();
				connection.commit();
	}catch(Exception e){
		connection.rollback();
		throw e;
	}
}catch(Exception e) {
	e.printStackTrace();
}finally {
	
}
	return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessusersignout).build();
}
}

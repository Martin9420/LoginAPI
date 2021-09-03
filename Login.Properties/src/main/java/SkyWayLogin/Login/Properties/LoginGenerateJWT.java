package SkyWayLogin.Login.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import EntityDTO.RequestDTO;
import EntityDTO.UserDTO;
import ErrorResponse.ResponseEnumCommon;
import ResponseCommon.CreateJWTResponse;
import util.StringUtil;

@RequestScoped
public class LoginGenerateJWT {

	@Inject
	private static Logger log;
	
	UserDTO body = new UserDTO();
	
	RequestDTO request = new RequestDTO();
	
	private StringUtil stringutil = new StringUtil();
	
	public LoginGenerateJWT() {
		log=LogManager.getLogger(LoginGenerateJWT.this);
	}
	
	public Response GenerateResponse(ResponseEnumCommon responseenumcommon) {
		CreateJWTResponse createjwtresponse = new CreateJWTResponse();
		createjwtresponse.setTime(LocalDateTime.now().toString());
		createjwtresponse.setCode(responseenumcommon.getStatus());
		createjwtresponse.setMessage(responseenumcommon.getResponseMessage());
		return Response.status(responseenumcommon.getStatus()).entity(createjwtresponse).build();
	}
	
	public Response service(UserDTO body, RequestDTO request) {
		CreateJWTResponse createjwtresponse = new CreateJWTResponse();
		ResponseEnumCommon responseenumcommon = ResponseEnumCommon.S200;
		StringBuilder getUUID = new StringBuilder();
		StringBuilder CreateJWT = new StringBuilder();
		
		Response TypeResponses = RequestBodyCheck(body, request);
		if(TypeResponses.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponses;
		}
		
		TypeResponses = SelectAdress(request);
		if(TypeResponses.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponses;
		}
		
		TypeResponses = GenerateUUID(getUUID);
		if(TypeResponses.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponses;
		}
		
		TypeResponses = getGenerateJWT(getUUID, CreateJWT);
		if(TypeResponses.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponses;
		}
		
		TypeResponses = UpdateUserinfo(CreateJWT, request, getUUID);
		if(TypeResponses.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponses;
		}
		createjwtresponse.setCode(responseenumcommon.getStatus());
		createjwtresponse.setTime(LocalDateTime.now().toString());
		createjwtresponse.setMessage(responseenumcommon.getResponseMessage());
		createjwtresponse.setJwt(CreateJWT.toString());
		createjwtresponse.setAdress(request.getbitAdress());
		return Response.status(Response.Status.OK.getStatusCode()).entity(createjwtresponse).build();
	}
	
	private Response RequestBodyCheck(UserDTO body, RequestDTO request) {
		CreateJWTResponse createjwtresponse = new CreateJWTResponse();
		if(stringutil.isNullorEmpty(request.getbitAdress())){
			return GenerateResponse(ResponseEnumCommon.E400);
		}
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(createjwtresponse).build();
	}
	
	private Response SelectAdress(RequestDTO request) {
		CreateJWTResponse createjwtresponse = new CreateJWTResponse();
		final String URL = "jdbc:postgresql://localhost:5432/postgres";
		final String USER = "postgres";
		final String PASSWORD = "password";
		final String SQL = "select * from mydb where bitadress =?;";
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedstatement = connection.prepareStatement(SQL)){
			preparedstatement.setString(1,  request.getbitAdress());
			try(ResultSet resultset = preparedstatement.executeQuery()){
				if(resultset.next()){
					
				}else {
					return GenerateResponse(ResponseEnumCommon.E401);
				}
		};
	}catch(SQLException e) {
		return GenerateResponse(ResponseEnumCommon.E500);
	}catch(Exception e) {
		return GenerateResponse(ResponseEnumCommon.E500);
	}finally {
		
	}
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(createjwtresponse).build();
	}
	
	private Response GenerateUUID(StringBuilder getUUID) {
		CreateJWTResponse createjwtresponse = new CreateJWTResponse();
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid);
		getUUID.append(uuid);
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(createjwtresponse).build();
	}
	
	private Response getGenerateJWT(StringBuilder getUUID, StringBuilder CreateJWT) {
		CreateJWTResponse createjwtresponse = new CreateJWTResponse();
		try {
			Date expireTime = new Date();
			expireTime.setTime(expireTime.getTime() + 600000l);
			
			 Algorithm algorithm = Algorithm.HMAC256(getUUID.toString());
			    String token = JWT.create()
			            .withIssuer("auth0")
			            .withExpiresAt(expireTime)
			            .sign(algorithm);
			    CreateJWT.append(token);
			} catch (JWTCreationException exception){
				log.fatal(exception);
		}
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(createjwtresponse).build();
	}
	
	private Response UpdateUserinfo(StringBuilder CreateJWT, RequestDTO request, StringBuilder getUUID) {
		CreateJWTResponse createjwtresponse = new CreateJWTResponse();
		final String URL = "jdbc:postgresql://localhost:5432/postgres";
		final String USER = "postgres";
		final String PASSWORD = "password";
		final String SQL = "update mydb set JWTToken = ?, UUID = ? where bitAdress = ?";
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
			connection.setAutoCommit(false);
			
			try(PreparedStatement preparedstatement = connection.prepareStatement(SQL)){
				preparedstatement.setString(1, CreateJWT.toString());
					preparedstatement.setString(2, getUUID.toString());
						preparedstatement.setString(3, request.getbitAdress());
							preparedstatement.executeUpdate();
				connection.commit();
			}catch(Exception e) {
				connection.rollback();
				return GenerateResponse(ResponseEnumCommon.E500);
			}
	}catch(Exception e) {
		e.printStackTrace();
		return GenerateResponse(ResponseEnumCommon.E500);
	}finally {
}
	return Response.status(Response.Status.CREATED.getStatusCode()).entity(createjwtresponse).build();
}

}


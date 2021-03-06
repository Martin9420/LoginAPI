package SkyWayLogin.Login.Properties;

import java.security.Key;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import software.amazon.codeguruprofilerjavaagent.Profiler;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import EntityDTO.RequestDTO;
import EntityDTO.UserDTO;
import ErrorResponse.ResponseEnumCommon;
import PasswordUtil.PasswordHashUtil;
import ResponseCommon.ErrorResponse;
import ResponseCommon.LoginProcessResponse;
import ResponseCommon.SuccessResponse;
import util.StringUtil;

/**
 * @author Masaki_Fukaya
 *
 */
@RequestScoped
public class LoginProcessUserSignIn {
	
	@Inject
	private StringUtil stringutil = new StringUtil();
	
	UserDTO body = new UserDTO();
	
	RequestDTO request = new RequestDTO();
	
	private static final String TAB = "\t";
	
	private static Logger log;
	
	private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
	
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
	
	public LoginProcessUserSignIn() {
		log = LogManager.getLogger(LoginProcessUserSignIn.this);
	}
	
	public Response GenerateResponse(ResponseEnumCommon responseenumcommon) {
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		loginprocessresponse.setTime(LocalDateTime.now().toString());
		loginprocessresponse.setCode(responseenumcommon.getStatus());
		loginprocessresponse.setMessage(responseenumcommon.getResponseMessage());
		return Response.status(responseenumcommon.getStatus()).entity(loginprocessresponse).build();
	}
	
	/**
	 * @param body
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Response service(UserDTO body, RequestDTO request)throws Exception {
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		ResponseEnumCommon responseenumcommon = ResponseEnumCommon.S200;
		StringBuilder safetypass = new StringBuilder();
		StringBuilder safetyid = new StringBuilder();
		StringBuilder idcheckdb = new StringBuilder();
		StringBuilder passwordcheckdb = new StringBuilder();
		StringBuilder date = new StringBuilder();
		
		Response TypeResponse = RequestBodyTypeCheck(body, request);
		if(TypeResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponse;
		}
		TypeResponse = CreateDateTimeAuto(date);
		if(TypeResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponse;
		}
		TypeResponse = AutoGeneratedUseridHash(request, safetypass);
		if(TypeResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponse;
		}
		TypeResponse = AutoGeneratedPasswordHash(request, safetyid);
		if(TypeResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponse;
		}
		TypeResponse = SerchIdforDB(request, safetypass, idcheckdb);
		if(TypeResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponse;
		}
		TypeResponse = SerchPasswordforDB(request, safetyid, passwordcheckdb);
		if(TypeResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponse;
		}
		TypeResponse = ChangeUserActivityDate(date, safetypass);
		if(TypeResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponse;
		}
		TypeResponse = ChangeUserActivityStatus(safetypass);
		if(TypeResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponse;
		}
		TypeResponse = UserIdHashCheckForDB(safetypass, idcheckdb);
		if(TypeResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponse;
		}
		TypeResponse = UserPasswordHashCheckForDB(safetyid, passwordcheckdb);
		if(TypeResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
			return TypeResponse;
		}
		loginprocessresponse.setCode(responseenumcommon.getStatus());
		loginprocessresponse.setTime(LocalDateTime.now().toString());
		loginprocessresponse.setMessage(responseenumcommon.getResponseMessage());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessresponse).build();
		}
	
	
	/**
	 * Method to check null of request body
	 * 
	 * @param body
	 * @param request
	 * @return 201 CREATED
	 */
	private Response RequestBodyTypeCheck(UserDTO body, RequestDTO request) {
		new LoginProcessUserSignIn();
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		//Method execution condition
		log.info("Method:RequestBodyCheck is Call for Service");
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:RequestBodyCheck Start Time is " + LocalDateTime.now());
		if(stringutil.isNullorEmpty(request.getuserid()) ||
				stringutil.isNullorEmpty(request.getjwt()) ||
					stringutil.isNullorEmpty(request.getbitAdress()) ||
						stringutil.isNullorEmpty(request.getuuid()) ||
							stringutil.isNullorEmpty(request.getpassword())) {
			return GenerateResponse(ResponseEnumCommon.E400);
		}
		log.info(request.getuserid());
			log.info(request.getpassword());
				log.info(request.getjwt());
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:RequestBodyCheck Finish Time is " + LocalDateTime.now());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessresponse).build();
	}
	
	/**
	 * Get the date of sign-in and store it in DB with StringBuilder
	 * 
	 * @param date
	 * @return
	 */
	private Response CreateDateTimeAuto(StringBuilder date) {
		new LoginProcessUserSignIn();
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		//Method execution condition
		log.info("Method:CreateDateTimeAuto is Finished after RequestBodyTypeCheck");
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:CreateDateTimeAuto Start Time is " + LocalDateTime.now());
		LocalDateTime nowDateTime = LocalDateTime.now();
		DateTimeFormatter java8Format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String java8Disp = nowDateTime.format(java8Format );
		date.append(java8Disp);
		log.info(java8Disp);
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessSignIn, Method:CreateDateTimeAuto Finish Time is " + LocalDateTime.now());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessresponse).build();
	}
	
	/**
	 * Method to hash UserID of request body
	 * 
	 * @param request
	 * @param safetypass
	 * @return
	 */
	private Response AutoGeneratedUseridHash(RequestDTO request, StringBuilder safetypass) {
		new LoginProcessUserSignIn();
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		//Method execution condition
		log.info("Method:AutoGeneratedUseridHash is Finished after CreateDateTimeAuto");
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:AutoGeneratedUseridHash Start Time is " + LocalDateTime.now());
		String safetyPassword1 = PasswordHashUtil.getSaftyPassword("password", request.getuserid());
        safetypass.append(safetyPassword1);
        log.info(safetyPassword1);
        log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:AutoGeneratedUseridHash Finish Time is " + LocalDateTime.now());
        return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessresponse).build();
	}
	
	/**
	 * Method to hash the user password of the request body
	 * 
	 * @param request
	 * @param safetyid
	 * @return
	 */
	private Response AutoGeneratedPasswordHash(RequestDTO request, StringBuilder safetyid) {
		new LoginProcessUserSignIn();
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		//Method execution condition
		log.info("Method:AutoGeneratedPasswordHash is Finished after AutoGeneratedUseridHash");
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:AutoGeneratedPasswordHash Start Time is " + LocalDateTime.now());
		String safetyPassword1 = PasswordHashUtil.getSaftyPassword("password", request.getpassword());
        safetyid.append(safetyPassword1);
        log.info(safetyPassword1);
        log.info("Package:SkyWayLogin.Properties, Class:LoginProcessSignIn, Method:AutoGeneratedPasswordHash Finish Time is " + LocalDateTime.now());
        return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessresponse).build();
	}
	
	/**
	 * Method to search DB and query user information
	 * 
	 * @param request
	 * @param safetypass
	 * @param passwordcheckdb
	 * @return
	 */
	private Response SerchIdforDB(RequestDTO request, StringBuilder safetypass, StringBuilder passwordcheckdb) {
		new LoginProcessUserSignIn();
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		//Method execution condition
		log.info("Method:SerchIdforDB is Finished after AutoGeneratedPasswordHash");
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessSignIn, Method:SerchIdforDB Start Time is " + LocalDateTime.now());
		String userid = null;
		String jwt1 = null;
		String uuid = null;
		final String URL = "jdbc:postgresql://localhost:5432/postgres";
		final String USER = "postgres";
		final String PASSWORD = "password";
		final String SQL = "select * from mydb where userid =? or JWTToken = ? or uuid = ?;";
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedstatement = connection.prepareStatement(SQL)){
			preparedstatement.setString(1, safetypass.toString());
				preparedstatement.setString(2, request.getjwt());
					preparedstatement.setString(3, request.getuuid());
			try(ResultSet resultset = preparedstatement.executeQuery()){
				if(resultset.next()) {
					userid = resultset.getString("userid");
						jwt1 = resultset.getString("JWTToken");
							uuid = resultset.getString("uuid");
					passwordcheckdb.append(userid);
				}else {
				}
			};
		}catch(SQLException e) {
			log.error("Internal Server Error");
			log.trace(e);
			e.printStackTrace();
			return GenerateResponse(ResponseEnumCommon.E500);
		}catch(Exception e) {
			log.error("Internal Server Error");
			log.trace(e);
			e.printStackTrace();
			return GenerateResponse(ResponseEnumCommon.E500);
		}finally {
			log.info("Success to SerchIdforDB Record for mydb");
		}
		
		String token = jwt1;
		try {
			Algorithm algorithm = Algorithm.HMAC256(uuid);
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer("auth0")
		            .build();
			DecodedJWT jwtoken = verifier.verify(token);
			System.out.println(jwtoken);
		}catch(JWTVerificationException exception) {
			exception.printStackTrace();
			return GenerateResponse(ResponseEnumCommon.E500);
		}
		
		log.info(URL);
			log.info(USER);
				log.info(PASSWORD);
					log.info(PASSWORD);
						log.info(SQL);
							log.info(safetypass.toString());
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:SerchIdforDB Finish Time is " + LocalDateTime.now());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessresponse).build();
	}
	
	/**
	 * Inquire user password
	 * 
	 * @param request
	 * @param safetyid
	 * @param passwordcheckdb
	 * @return
	 */
	private Response SerchPasswordforDB(RequestDTO request, StringBuilder safetyid, StringBuilder passwordcheckdb) {
		new LoginProcessUserSignIn();
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		//Method execution condition
		log.info("Method:SerchPasswordforDB is Finished after SerchIdforDB");
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:SerchPasswordforDB Start Time is " + LocalDateTime.now());
		String password = null;
		final String URL = "jdbc:postgresql://localhost:5432/postgres";
		final String USER = "postgres";
		final String PASSWORD = "password";
		final String SQL = "select * from mydb where password =?;";
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedstatement = connection.prepareStatement(SQL)){
			preparedstatement.setString(1,   safetyid.toString());
			try(ResultSet resultset = preparedstatement.executeQuery()){
				if(resultset.next()) {
					password = resultset.getString("password");
					passwordcheckdb.append(password);
				}else {
					
				}
			};
		}catch(SQLException e) {
			log.error("Internal Server Error");
			log.trace(e);
			e.printStackTrace();
			return GenerateResponse(ResponseEnumCommon.E500);
		}catch(Exception e) {
			log.error("Internal Server Error");
			log.trace(e);
			e.printStackTrace();
			return GenerateResponse(ResponseEnumCommon.E500);
		}finally {
			log.info("Success to SerchPasswordforDB Record for mydb");
		}
		log.info(URL);
			log.info(USER);
				log.info(PASSWORD);
					log.info(SQL);
						log.info(safetyid.toString());
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:SerchPasswordforDB Finish Time is " + LocalDateTime.now());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessresponse).build();
	}
	
	/**
	 * Change the user activity date stored in the DB
	 * 
	 * @param date
	 * @param safetypass
	 * @return
	 * @throws Exception
	 */
	private Response ChangeUserActivityDate(StringBuilder date, StringBuilder safetypass) throws Exception {
		new LoginProcessUserSignIn();
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		//Method execution condition
		log.info("Method:ChangeUserActivityDate is Finished after SerchPasswordforDB");
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:ChangeUserActivityDate Start Time is " + LocalDateTime.now());
		final String URL = "jdbc:postgresql://localhost:5432/postgres";
		final String USER = "postgres";
		final String PASSWORD = "password";
		final String SQL = "update mydb set useractivitydate = ? where userid = ?";
			
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
			connection.setAutoCommit(false);
			
			try(PreparedStatement preparedstatement = connection.prepareStatement(SQL)){
				preparedstatement.setString(1, date.toString());
					preparedstatement.setString(2, safetypass.toString());
						preparedstatement.executeUpdate();
				connection.commit();
			}catch(Exception e) {
				connection.rollback();
				log.error("rollback");
				log.trace(e);
				throw e;
			}
	}catch(Exception e) {
		log.error("Internal Server Error");
		log.trace(e);
		e.printStackTrace();
	}finally {
		log.info("Success to ChangeUserActivityDate Record for mydb");
	}
	log.info(URL);
		log.info(USER);
			log.info(PASSWORD);
				log.info(SQL);
					log.info(date.toString());
						log.info(safetypass.toString());
	log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:ChangeUserActivityDate Finish Time is " + LocalDateTime.now());
	return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessresponse).build();
	}
	
	/**
	 * Change the user activity status stored in the DB to 1
	 * 
	 * @param safetypass
	 * @return
	 * @throws Exception
	 */
	private Response ChangeUserActivityStatus(StringBuilder safetypass)throws Exception{
		new LoginProcessUserSignIn();
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		//Method execution condition
		log.info("Method:ChangeUserActivityStatus is Finished after ChangeUserActivityDate");
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:ChangeUserActivityStatus Start Time is " + LocalDateTime.now());
		int Activity = 1;
		final String URL = "jdbc:postgresql://localhost:5432/postgres";
		final String USER = "postgres";
		final String PASSWORD = "password";
		final String SQL = "update mydb set useractivity = ? where userid = ?";
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
			connection.setAutoCommit(false);
			try(PreparedStatement preparedstatement = connection.prepareStatement(SQL)){
				preparedstatement.setInt(1, Activity);
					preparedstatement.setString(2, safetypass.toString());
						preparedstatement.executeUpdate();
				connection.commit();
			}catch(Exception e) {
				connection.rollback();
				log.error("rollback");
				log.trace(e);
				throw e;
			}
		}catch(Exception e) {
			log.error("Internal Server Error");
			log.trace(e);
		e.printStackTrace();
		}finally {
			log.info("Success to ChangeUserActivityStatus Record for mydb");
		}
		log.info(USER);
			log.info(USER);
				log.info(PASSWORD);
					log.info(SQL);
						log.info(Activity);
							log.info(safetypass.toString());
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:ChangeUserActivityStatus Finish Time is " + LocalDateTime.now());			
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessresponse).build();
	}
	
	/**
	 * Search the DB in the hashed state of the user ID
	 * 
	 * @param safetypass
	 * @param idcheckdb
	 * @return
	 */
	private Response UserIdHashCheckForDB(StringBuilder safetypass, StringBuilder idcheckdb) {
		new LoginProcessUserSignIn();
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		//Method execution condition
		log.info("Method:UserIdHashCheckForDB is Finished after ChangeUserActivityStatus");
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:UserIdHashCheckForDB Start Time is " + LocalDateTime.now());
		if(idcheckdb.toString().equals(safetypass.toString())) {
			log.info(idcheckdb.toString());
			log.info(safetypass.toString());
		}else {
			log.error("Incollect UserId");
			return GenerateResponse(ResponseEnumCommon.E500);
		}
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:UserIdHashCheckForDB Finish Time is " + LocalDateTime.now());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessresponse).build();
	}
	
	/**
	 * Search the DB in the hashed state of the Password
	 * 
	 * @param safetyid
	 * @param passwordcheckdb
	 * @return
	 */
	private Response UserPasswordHashCheckForDB(StringBuilder safetyid, StringBuilder passwordcheckdb) {
		new LoginProcessUserSignIn();
		LoginProcessResponse loginprocessresponse = new LoginProcessResponse();
		//Method execution condition
		log.info("Method:UserPasswordHashCheckForDB is Finished after UserIdHashCheckForDB");
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:UserPasswordHashCheckForDB Start Time is " + LocalDateTime.now());
		if(passwordcheckdb.toString().equals(safetyid.toString())) {
			log.info(passwordcheckdb.toString());
			log.info(safetyid.toString());
		}else {
			log.error("Incollect UserPassword");
			return GenerateResponse(ResponseEnumCommon.E500);
		}
		log.info("Package:SkyWayLogin.Properties, Class:LoginProcessUserSignIn, Method:UserPasswordHashCheckForDB Finish Time is " + LocalDateTime.now());
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(loginprocessresponse).build();
	}
}

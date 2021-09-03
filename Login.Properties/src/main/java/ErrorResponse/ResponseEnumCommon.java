package ErrorResponse;

import javax.ws.rs.core.Response;

public enum ResponseEnumCommon {
	//ErrorResponse
	E400(Response.Status.BAD_REQUEST.getStatusCode(), "400" , "Faile to Create User New UserInformation for 400 BadRequest"),
	E401(Response.Status.UNAUTHORIZED.getStatusCode(), "401" , "Faile to authorization UserInformation for 401 unauthorized"),
	E403(Response.Status.FORBIDDEN.getStatusCode(), "403", "User information already exists"),
	E409(Response.Status.CONFLICT.getStatusCode(), "409" , "Faile to Create User New UserInformmation for 409 Conflict"),
	E500(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "500" , "Faile to Connect PostgreSQL for 500 InternalServerError"),
	//SuccessResponse
	S200(Response.Status.OK.getStatusCode(), "200", "All Process Successfuly"),
	S201(Response.Status.CREATED.getStatusCode(), "201" , "All Request is Create Successfully"),
	S202(Response.Status.ACCEPTED.getStatusCode(), "202", "Request accepted");
	
	
	private int Status;
	
	private String ResponseStatus;
	
	private String ResponseMessage;
	
	private ResponseEnumCommon(int Status, String ResponseStatus, String ResponseMessage) {
		this.Status = Status;
		this.ResponseStatus = ResponseStatus;
		this.ResponseMessage = ResponseMessage;
	}
	
	public int getStatus() {
		return Status;
	}
	
	public String getResponseStatus() {
		return ResponseStatus;
	}
	
	public String getResponseMessage() {
		return ResponseMessage;
	}
}

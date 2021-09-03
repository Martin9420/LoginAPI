package ResponseCommon;

public class SuccessResponse {
	
	private String code;
	
	private String time;
	
	private String message;
	
	private String jwt;
	
	private String uuid;
	
	public SuccessResponse() {
		this.setCode(new String());
		this.setTime(new String());
		this.setMessage(new String());
		this.setJwt(new String());
		this.setUUid(new String());
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getJwt() {
		return jwt;
	}
	
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
	public String getUUid() {
		return uuid;
	}
	
	public void setUUid(String uuid) {
		this.uuid = uuid;
	}

}

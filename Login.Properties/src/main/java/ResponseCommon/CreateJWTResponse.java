package ResponseCommon;

public class CreateJWTResponse {
	
	private int code;
	
	private String time;
	
	private String message;
	
	private String jwt;
	
	private String adress;
	
	public CreateJWTResponse() {
		this.setCode(new Integer(code));
		this.setTime(new String());
		this.setMessage(new String());
		this.setJwt(new String());
		this.setAdress(new String());
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
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
	
	public String getAdress() {
		return adress;
	}
	
	public void setAdress(String adress) {
		this.adress = adress;
	}
}

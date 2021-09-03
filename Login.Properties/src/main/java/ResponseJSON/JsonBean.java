package ResponseJSON;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonBean {
	
	@JsonProperty("Date")
	private String Date;
	
	@JsonProperty("id")
	private String Id;
	
	public void setDate(String Date ) {
		this.Date = Date;
	}
	public void setId(String Id) {
		this.Id = Id;
	}
	

}

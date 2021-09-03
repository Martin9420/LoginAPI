package util;

public class IntegerUtil {
	
	private Integer integer;
	
	public IntegerUtil() {
		
	}
	
	public Integer getinteger() {
		return integer;
	}
	
	public void setinteger(Integer integer) {
		this.integer = integer;
	}
	
	public boolean isNullorEmpty(Integer integer) {
		return(integer == null || 0 == integer);
	}

}

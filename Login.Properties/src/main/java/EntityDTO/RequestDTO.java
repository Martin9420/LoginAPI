package EntityDTO;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.HeaderParam;

@RequestScoped
public class RequestDTO {
	
	@HeaderParam("userid")
	private String userid;
	
	@HeaderParam("id")
	private int id;
	
	@HeaderParam("useridentityid")
	private long useridentityid;
	
	@HeaderParam("useridentitypassword")
	private long useridentitypassword;
	
	@HeaderParam("name")
	private String name;
	
	@HeaderParam("password")
	private String password;
	
	@HeaderParam("createdate")
	private String createdate;
	
	@HeaderParam("deletedate")
	private String deletedate;
	
	@HeaderParam("sex")
	private String sex;
	
	@HeaderParam("age")
	private Integer age;
	
	@HeaderParam("userfinallogindate")
	private String userfinallogindate;
	
	@HeaderParam("userfinallogoutdate")
	private String userfinallogoutdate;
	
	@HeaderParam("adress")
	private String adress;
	
	@HeaderParam("useractive")
	private boolean useractive;
	
	@HeaderParam("nickname")
	private String nickname;
	
	@HeaderParam("mailadress")
	private String mailadress;
	
	@HeaderParam("jwt")
	private String jwt;
	
	@HeaderParam("bitAdress")
	private String bitAdress;
	
	@HeaderParam("uuid")
	private String uuid;
	
	public void setuserid(String userid) {
		this.userid = userid;
	}
	
	public String getuserid() {
		return this.userid;
	}
	
	public int getid() {
		return this.id;
	}
	
		public void setid(final int id) {
			this.id = id;
		}
	
	public long getuseridentityid() {
		return this.useridentityid;
	}
	
		public void setuseridentityid(final long useridentityid) {
			this.useridentityid= useridentityid;
		}
	
	public long getuseridentitypassword() {
		return this.useridentitypassword;
	}
	
		public void setuseridentitypassword(final long useridentitypassword) {
			this.useridentitypassword = useridentitypassword;
		}
	
	public String getname() {
		return this.name;
	}
	
		public void setname(final String name) {
			this.name = name;
		}
		
		public String getpassword() {
			return this.password;
		}
		
			public void setpassword(final String password) {
				this.password = password;
			}
		
		public String getcreatedate() {
			return this.createdate;
		}
		
			public void setcreatedate(final String createdate) {
				this.createdate = createdate;
			}
		
		public String getdeletedate() {
			return this.deletedate;
		}
		
			public void setdeletedate(final String deletedate) {
				this.deletedate = deletedate;
			}
		
		public String getsex() {
			return this.sex;
		}
		
			public void setsex(final String sex) {
				this.sex = sex;
			}
		
		public Integer getage() {
			return this.age;
		}
		
			public void setage(final Integer age) {
				this.age = age;
			}
		
		public String getuserfinallogindate() {
			return this.userfinallogindate;
		}
		
			public void setuserfinallogindate(final String userfinallogindate) {
				this.userfinallogindate = userfinallogindate;
			}
		
		public String getuserfinallogoutdate() {
			return this.userfinallogoutdate;
		}
		
			public void setuserfinallogoutdate(final String userfinallogoutdate) {
				this.userfinallogoutdate = userfinallogoutdate;
			}
		
		public String getadress() {
			return this.adress;
		}
		
			public void setadress(final String adress) {
				this.adress = adress;
			}
		
		public boolean getuseractive() {
			return this.useractive;
		}
		
			public void setuceractive(final boolean useractive) {
				this.useractive = useractive;
			}
			
		public String getnickname() {
			return this.nickname;
		}
			
			public void setnickname(final String nickname) {
				this.nickname = nickname;
			}
		
		public String getmailadress() {
			return this.mailadress;
		}
		
			public void setmailadress(final String mailadress) {
				this.mailadress = mailadress;
			}
		public String getjwt() {
			return this.jwt;
		}
			
			public void setjwt(final String jwt) {
				this.jwt = jwt;
			}
			
		public String getbitAdress() {
			return this.bitAdress;
		}
		
			public void setbitAdress(final String bitAdress) {
				this.bitAdress = bitAdress;
			}
			
		public String getuuid() {
			return this.uuid;
		}
			
			public void setuuid(final String uuid) {
				this.uuid = uuid;
			}

}
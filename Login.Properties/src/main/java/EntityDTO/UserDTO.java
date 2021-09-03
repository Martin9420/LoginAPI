package EntityDTO;

import javax.enterprise.context.RequestScoped;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@RequestScoped
public class UserDTO {
	
	private int id;
	
	private long useridentityid;
	
	private long useridentitypassword;
	
	private String name;
	
	private String userid;
	
	private String password;
	
	private String createdate;
	
	private String deletedate;
	
	private String sex;
	
	private String age;
	
	private String userfinallogindate;
	
	private String userfinallogoutdate;
	
	private String adress;
	
	private boolean useractive;
	
	private String nickname;
	
	private String mailadress;
	
	private String jwt;
	
	private String bitAdress;
	
	private String uuid;
	
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
	
	public String getuserid() {
		return this.userid;
	}
	
		public void setuserid(final String userid) {
			this.userid = userid;
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
	
	public String getage() {
		return this.age;
	}
	
		public void setage(final String age) {
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
			this.bitAdress= bitAdress;
		}
	
	public String getuuid() {
		return this.uuid;
	}
	
		public void setuuid(final String uuid) {
			this.uuid = uuid;
		}
	
	

}

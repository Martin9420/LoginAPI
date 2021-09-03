package com.ibm.jaxrs.User.Jaxrs.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Id;

@Table(name = "ACCOUNT_TAB")
@Entity
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	
	@Column(name="Id",length=10)
	private int Id;
	
	@Column(name="EmployeeNumber",length=10)
	private String EmployeeNumber;
	
	@Column(name="Name",length=10)
	private String Name;
	
	@Column(name="mailAdless",length=10)
	private String mailAdless;
	
	@Column(name="Password",length=10)
	private String Password;
	
	public int getId() {
		return Id;
	}
	public void setId(int Id) {
		this.Id = Id;
	}
	public String getEmployeeNumber() {
		return EmployeeNumber;
	}
	public void setEmployeeNumber(String EmployeeNumber) {
		this.EmployeeNumber = EmployeeNumber;
	}
	public String getName() {
		return Name;
	}
	public void setName(String Name) {
		this.Name = Name; 
	}
	public String getmailAdless() {
		return mailAdless;
	}
	public void setmailAdless(String mailAdless) {
		this.mailAdless = mailAdless;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String Password) {
		this.Password = Password;
	}

}

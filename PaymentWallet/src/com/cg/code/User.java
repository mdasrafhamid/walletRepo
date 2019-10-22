package com.cg.code;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	private int id = 0;
	
	@Column(name="first_name")
	private String fname;
	
	@Column(name="last_name")
	private String lname;
	
	private String email;
	
	public User(String fname, String lname, String email){

		this.fname = fname;
		this.lname = lname;
		this.email = email;
	}
	
	public User() {
		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + fname + " " + lname + ", email=" + email + "]";
	}
	
	
}

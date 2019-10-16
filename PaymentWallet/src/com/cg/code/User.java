package com.cg.code;

public class User {
	private static int c=0;
	
	private int id;
	private String fname;
	private String lname;
	private String email;
	 
	public User(String fname, String lname, String email){
		this.id = c++;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
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

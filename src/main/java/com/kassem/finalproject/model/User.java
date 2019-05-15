package com.kassem.finalproject.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {



	@Id
	private long id;
	private String username;
	private String password;
	private String email;
	private String role;
	
	public User(String username, String password,String email,String role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	public User() {
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	

}

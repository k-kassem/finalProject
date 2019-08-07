package com.kassem.finalproject.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MonthlyAttend {

	@Id
	private long id;
	private String userId;
	private String userName;
	private Double nbOfHour;
	private LocalDate date;
	
	public MonthlyAttend() {
		
	}
	public MonthlyAttend(long id, String userId, String userName, Double nbOfHour, LocalDate date) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.nbOfHour = nbOfHour;
		this.date = date;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Double getNbOfHour() {
		return nbOfHour;
	}
	public void setNbOfHour(Double nbOfHour) {
		this.nbOfHour = nbOfHour;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
}

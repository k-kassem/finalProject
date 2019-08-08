package com.kassem.finalproject.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MonthlyAttend {

	@Id
	private long id;
	private String userId;
	private String firstName;
	private String lastName;
	private Double nbOfHour;
	private String startTime;
	private String endTime;
	private LocalDate date;
	
	
	public MonthlyAttend() {
		
	}
	public MonthlyAttend(long id, String userId, String firstName, Double nbOfHour, LocalDate date,String lastName,String startTime,String endTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.nbOfHour = nbOfHour;
		this.date = date;
		this.lastName = lastName;
		this.startTime = startTime;
		this.endTime = endTime;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}

package com.kassem.finalproject.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DailyAttend {

	@Id
	private Long id;
	private String userId;
	private String startTime;
	private String userName;
	private LocalDate date;
	
	public DailyAttend() {
		
	}

	public DailyAttend(Long id, String userId, String startTime, String endTime, LocalDate date, String userName) {
		super();
		this.id = id;
		this.userId = userId;
		this.startTime = startTime;
		this.date = date;
		this.userName = userName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}

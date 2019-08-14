package com.kassem.finalproject.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LeaveRequest {

	@Id
	private Long id;
	private String userid;
	private String userName;
	private String userLastName;
	private String departement;
	private String addressDuringLeave;
	private Integer nbOfDays;
	private LocalDate startDate;
	private LocalDate endDate;
	private String leaveType;
	
	public LeaveRequest(Long id, String userid, String userName, String userLastName, String departement,
			String addressDuringLeave, Integer nbOfDays, LocalDate startDate, LocalDate endDate,String leaveType) {
		super();
		this.id = id;
		this.userid = userid;
		this.userName = userName;
		this.userLastName = userLastName;
		this.departement = departement;
		this.addressDuringLeave = addressDuringLeave;
		this.nbOfDays = nbOfDays;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
	}
	public LeaveRequest() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public String getAddressDuringLeave() {
		return addressDuringLeave;
	}

	public void setAddressDuringLeave(String addressDuringLeave) {
		this.addressDuringLeave = addressDuringLeave;
	}

	public Integer getNbOfDays() {
		return nbOfDays;
	}

	public void setNbOfDays(Integer nbOfDays) {
		this.nbOfDays = nbOfDays;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public enum LeaveType{
		Sick{
			public String toString() {
	            return "Sick";
	        }
		},
		Annual_Leave {
			public String toString() {
	            return "Annual Leave";
	        }
		},
		Paid_Leave{
			public String toString() {
	            return "Paid Leave";
	        }
		},
	}
	
}

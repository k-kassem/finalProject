package com.kassem.finalproject.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Applicant {

	@Id
	private Long id;
	private String firstName;
	private String lastName;
	private String nationality;
	private Integer age;
	private String email;
	private Integer phoneNumber;
	
	//High education
	private String degree;//enum
	private String major;
	private String university;
	private LocalDate eduStartDate;
	private LocalDate eduEndDate;
	
	//Last Experience;
	private Integer nbOfyear;
	private String company;
	private String role;
	private String comment;
	private LocalDate expStartDate;
	private LocalDate expEndDate;
	
	private String connectingWay;
	private long jobOfferId;
	
	public Applicant() {
		
	}
	public Applicant(Long id, String firstName, String lastName, String nationality, Integer age, String email,
			Integer phoneNumber, String degree, String major, String university, LocalDate eduStartDate,
			LocalDate eduEndDate, Integer nbOfyear, String company, String role, String comment, LocalDate expStartDate,
			LocalDate expEndDate, String connectingWay,long jobOfferId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nationality = nationality;
		this.age = age;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.degree = degree;
		this.major = major;
		this.university = university;
		this.eduStartDate = eduStartDate;
		this.eduEndDate = eduEndDate;
		this.nbOfyear = nbOfyear;
		this.company = company;
		this.role = role;
		this.comment = comment;
		this.expStartDate = expStartDate;
		this.expEndDate = expEndDate;
		this.connectingWay = connectingWay;
		this.jobOfferId = jobOfferId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public LocalDate getEduStartDate() {
		return eduStartDate;
	}

	public void setEduStartDate(LocalDate eduStartDate) {
		this.eduStartDate = eduStartDate;
	}

	public LocalDate getEduEndDate() {
		return eduEndDate;
	}

	public void setEduEndDate(LocalDate eduEndDate) {
		this.eduEndDate = eduEndDate;
	}

	public Integer getNbOfyear() {
		return nbOfyear;
	}

	public void setNbOfyear(Integer nbOfyear) {
		this.nbOfyear = nbOfyear;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDate getExpStartDate() {
		return expStartDate;
	}

	public void setExpStartDate(LocalDate expStartDate) {
		this.expStartDate = expStartDate;
	}

	public LocalDate getExpEndDate() {
		return expEndDate;
	}


	public void setExpEndDate(LocalDate expEndDate) {
		this.expEndDate = expEndDate;
	}

	public String getConnectingWay() {
		return connectingWay;
	}

	public void setConnectingWay(String connectingWay) {
		this.connectingWay = connectingWay;
	}

	public long getJobOfferId() {
		return jobOfferId;
	}
	public void setJobOfferId(long jobOfferId) {
		this.jobOfferId = jobOfferId;
	}

	public enum Degrees{
		Undergraduate{
			public String toString() {
	            return "Undergraduate";
	        }
		},
		Associate {
			public String toString() {
	            return "Associate";
	        }
		},
		Bachelor{
			public String toString() {
	            return "Bachelor";
	        }
		},
		Master{
			public String toString() {
	            return "Master";
	        }
		},
		Doctoral{
			public String toString() {
	            return "Doctoral";
	        }
		},
		Professional{
			public String toString(){
				return "Professional";
			}
		}
	}
	
	public enum ConnectionWay{
		Email{
			public String toString() {
	            return "Email";
	        }
		},
		Phone_Number {
			public String toString() {
	            return "Phone Call";
	        }
		}
	}
	public Applicant copy(Applicant applicant,Applicant newApplicant) {
		
		newApplicant.setFirstName(applicant.getFirstName());
		return newApplicant;
	}
}

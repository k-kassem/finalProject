package com.kassem.finalproject.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JobOffer {

	@Id
	private long id;
	private String jobTitle;
	private String education;
	private String description;
	private String location;
	private String companyName;
	private String seniorityLevel;
	private LocalDate deadLine;
	private Integer numberOfApplication;
	private String employementType;
	private String skills;
	private String experience;
	
	public JobOffer(String jobTitle, String education, String description, String location, String companyName,
			String seniorityLevel, LocalDate deadLine, Integer numberOfApplication, String employementType, String skills,String experience) {
		this.jobTitle = jobTitle;
		this.education = education;
		this.description = description;
		this.location = location;
		this.companyName = companyName;
		this.seniorityLevel = seniorityLevel;
		this.deadLine = deadLine;
		this.numberOfApplication = numberOfApplication;
		this.employementType = employementType;
		this.skills = skills;
		this.experience = experience;
	}
	public JobOffer() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name="companyName")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSeniorityLevel() {
		return seniorityLevel;
	}

	public void setSeniorityLevel(String seniorityLevel) {
		this.seniorityLevel = seniorityLevel;
	}

	public LocalDate getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(LocalDate deadLine) {
		this.deadLine = deadLine;
	}

	public Integer getNumberOfApplication() {
		return numberOfApplication;
	}

	public void setNumberOfApplication(Integer numberOfApplication) {
		this.numberOfApplication = numberOfApplication;
	}
	
	
	
	public String getEmployementType() {
		return employementType;
	}

	public void setEmployementType(String employementType) {
		this.employementType = employementType;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}





	public enum SeniorityLevel{
		EntryLevel{
			public String toString() {
	            return "EntryLevel";
	        }
		},
		Junior{
			public String toString() {
	            return "Junior";
	        }
		},
		Med_Senior{
			public String toString() {
	            return "Med-Senior";
	        }
		},
		Senior{
			public String toString() {
	            return "Senior";
	        }
		},
		Intermediate{
			public String toString() {
	            return "Intermediate";
	        }
		}
	}
	
	public enum EmploymentTypes{
		Training{
			public String toString() {
	            return "Training";
	        }
		},
		Full_Time{
			public String toString() {
	            return "Full Time";
	        }
		},
		Part_Time{
			public String toString() {
	            return "Part Time";
	        }
		},
	}
	
	
	
}

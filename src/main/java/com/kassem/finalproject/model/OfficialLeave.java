package com.kassem.finalproject.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OfficialLeave {

	@Id
	private long id;
	private String occasion;
	private Integer nbOfDays;
	private LocalDate startDate;
	private String description;
	public OfficialLeave(long id, String occasion, Integer nbOfDays, LocalDate startDate, String description) {
		super();
		this.id = id;
		this.occasion = occasion;
		this.nbOfDays = nbOfDays;
		this.startDate = startDate;
		this.description = description;
	}
	public OfficialLeave() {
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOccasion() {
		return occasion;
	}
	public void setOccasion(String occasion) {
		this.occasion = occasion;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}

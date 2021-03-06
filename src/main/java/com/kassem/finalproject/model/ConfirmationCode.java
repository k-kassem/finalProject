package com.kassem.finalproject.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.vaadin.annotations.AutoGenerated;

@Entity
public class ConfirmationCode {
	
	@Id
	@AutoGenerated
	private Long id;
	private String userId;
	private Long code;
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
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	
	public String getCodeAsString() {
		return this.code.toString();
	}
	public void setCodeAsString(String newNode) {
		
	}
	
}

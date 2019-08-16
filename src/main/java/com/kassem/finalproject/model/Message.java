package com.kassem.finalproject.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Message {

	@Id
	private Long id;
	private String toUserId;
	private String fromUserId;
	private String Context;
	private String title;
	private LocalDate sentDate;
	public Message() {
		
	}
	
	public Message(Long id, String toUserId, String fromUserId, String context, String title,LocalDate sentDate) {
		super();
		this.id = id;
		this.toUserId = toUserId;
		this.fromUserId = fromUserId;
		Context = context;
		this.title = title;
		this.sentDate = sentDate;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getContext() {
		return Context;
	}
	public void setContext(String context) {
		Context = context;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getSentDate() {
		return sentDate;
	}

	public void setSentDate(LocalDate sentDate) {
		this.sentDate = sentDate;
	}
	
	
}

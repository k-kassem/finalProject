package com.kassem.finalproject.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Message {

	@Id
	private Long id;
	private String toUserId;
	private String fromUserName;
	private String fromUserId;
	private String context;
	private String title;
	private LocalDate sentDate;
	public Message() {
		
	}
	
	public Message(Long id, String toUserId, String fromUserId, String context, String title,LocalDate sentDate,String fromUserName) {
		super();
		this.id = id;
		this.toUserId = toUserId;
		this.fromUserId = fromUserId;
		this.context = context;
		this.title = title;
		this.sentDate = sentDate;
		this.fromUserName = fromUserName;
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
		return context;
	}
	public void setContext(String context) {
		this.context = context;
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

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String toUserName) {
		this.fromUserName = toUserName;
	}
	public Message copy(Message message,Message newMessage) {
		newMessage.setContext(message.getContext());
		newMessage.setFromUserId(message.getFromUserId());
		newMessage.setFromUserName(message.getFromUserName());
		newMessage.setId(message.getId());
		newMessage.setTitle(message.getTitle());
		newMessage.setToUserId(message.getToUserId());
		newMessage.setSentDate(message.getSentDate());
		return newMessage;
	}
}

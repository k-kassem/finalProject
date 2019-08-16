package com.kassem.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kassem.finalproject.model.Message;
import com.kassem.finalproject.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	public void saveMessage(Message message){
		messageRepository.save(message);
	}
	
	public List<Message> getAllMessageByUserId(String userId){
		List<Message> result = new ArrayList<>();
		messageRepository.findAll().forEach(rec -> {
			if(rec.getToUserId().equals(String.valueOf(userId))){
				result.add(rec);
			}
		});
		return result;
	}
}

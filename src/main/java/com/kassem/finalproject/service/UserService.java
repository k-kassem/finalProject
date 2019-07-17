package com.kassem.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kassem.finalproject.model.User;
import com.kassem.finalproject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUser(){
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(user -> users.add(user));
		return users;
	}
	
	public String add(/*User user*/) {
		//userRepository.save(/*user*/);
		return "Todo";
	}
	public void save(User user) {
		userRepository.save(user);
	}
	public void delete(User user) {
		userRepository.delete(user);
	}
	
	public User getUserByUsername(String userName) {
		return userRepository.findByusername(userName);
	}
}

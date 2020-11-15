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
		user.setFirstLogin(1);
		userRepository.save(user);
	}
	public void delete(User user) {
		userRepository.delete(user);
	}
	
	public User getUserByUsername(String userName) {
		return userRepository.findByusername(userName);
	}
	public User getUserById(Long id) {
		return userRepository.findById(id).get();
	}
	public List<User> getAllUsers(){
		return userRepository.getAllusers();
	}
	public String updateUser(Long id,String firstName,String lastName,String email,String address,String password,String status) {
		User user = getUserById(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setStatus(status);
		userRepository.save(user);
		return "User Updated";
	}
	public void UpdatePassword(User user) {
		userRepository.save(user);
	}
}

package com.kassem.finalproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.kassem.finalproject.model.User;


public interface UserRepository extends CrudRepository<User, Long>{
	User findByusername(String username);
}

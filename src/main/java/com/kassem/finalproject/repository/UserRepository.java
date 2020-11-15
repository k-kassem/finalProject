package com.kassem.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import com.kassem.finalproject.model.User;


public interface UserRepository extends CrudRepository<User, Long>{
	User findByusername(String username);
	
	@Query(value = "CALL GET_ALL_USER", nativeQuery = true)
	List<User> getAllusers();
}

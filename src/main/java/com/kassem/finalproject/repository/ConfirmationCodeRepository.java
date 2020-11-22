package com.kassem.finalproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.kassem.finalproject.model.ConfirmationCode;
import java.lang.String;
import java.util.List;

public interface ConfirmationCodeRepository extends CrudRepository<ConfirmationCode, Long>{

	List<ConfirmationCode> findByUserId(String userid);
	
}

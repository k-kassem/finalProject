package com.kassem.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kassem.finalproject.model.ConfirmationCode;
import com.kassem.finalproject.repository.ConfirmationCodeRepository;

@Service
public class ConfirmationCodeService {
	
	@Autowired
	private ConfirmationCodeRepository confirmationCodeRepository;
	
	public void saveConfirmationCode (ConfirmationCode confirmationCode ) {
		confirmationCodeRepository.save(confirmationCode);
	}
	public ConfirmationCode getCodeByUserName(String UserName) {
		if(confirmationCodeRepository.findByUserId(UserName).size() == 0)
			return null;
		return confirmationCodeRepository.findByUserId(UserName).get(0);
	}
}

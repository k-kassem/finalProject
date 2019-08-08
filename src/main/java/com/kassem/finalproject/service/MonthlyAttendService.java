package com.kassem.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kassem.finalproject.model.MonthlyAttend;
import com.kassem.finalproject.repository.MonthlyAttendRepository;

@Service
public class MonthlyAttendService {

	@Autowired
	MonthlyAttendRepository monthlyAttendRepository;
	
	public void saveRecord(MonthlyAttend monthlyAttend){
		monthlyAttendRepository.save(monthlyAttend);
	}
}

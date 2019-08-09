package com.kassem.finalproject.service;

import java.util.ArrayList;
import java.util.List;

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
	public List<MonthlyAttend> getAllRecord(){
		List<MonthlyAttend> result = new ArrayList<>();
		monthlyAttendRepository.findAll().forEach(rec -> result.add(rec));
		return result;
	}
	public List<MonthlyAttend> getMonthlyAttendByUserId(Long id){
		List<MonthlyAttend> result = new ArrayList<>();
		monthlyAttendRepository.findAll().forEach(rec -> {
			if(rec.getUserId().equals(String.valueOf(id))){
				result.add(rec);
			}
		});
		return result;
	}
}

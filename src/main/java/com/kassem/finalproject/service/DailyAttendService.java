package com.kassem.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kassem.finalproject.model.DailyAttend;
import com.kassem.finalproject.repository.DailyAttendRepository;

@Service
public class DailyAttendService {

	@Autowired
	private DailyAttendRepository dailyAttendRepository;
	
	public void saveDailyAttend(DailyAttend dailyAttend) {
		dailyAttendRepository.save(dailyAttend);
	}
	
	public List<DailyAttend> getAllattended(){
		List<DailyAttend> result = new ArrayList<>();
		dailyAttendRepository.findAll().forEach(rec -> result.add(rec));
		return result;
	}
	public void removeRecord(DailyAttend attend) {
		dailyAttendRepository.delete(attend);
	}
	public DailyAttend getbyId(String id) {
		List<DailyAttend> attends = new ArrayList<>();
		dailyAttendRepository.findAll().forEach(rec -> attends.add(rec));
		for(DailyAttend attend : attends) {
			if(attend.getUserId() == id)
				return attend;
		}
		return attends.get(0);
		 
	}
}

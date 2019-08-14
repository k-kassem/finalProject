package com.kassem.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kassem.finalproject.model.LeaveRequest;
import com.kassem.finalproject.repository.LeaveRequestRepository;

@Service
public class LeaveRequestService {

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	
	public void save (LeaveRequest leaveRequest){
		leaveRequestRepository.save(leaveRequest);
	}
	
	public List<LeaveRequest> getAllLeaves(){
		List<LeaveRequest> result = new ArrayList<>();
		leaveRequestRepository.findAll().forEach(res -> result.add(res));
		return result;
	}
}

package com.kassem.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kassem.finalproject.model.OfficialLeave;
import com.kassem.finalproject.repository.OfficialLeaveRepository;

@Service
public class OfficialLeaveService {

	@Autowired
	private OfficialLeaveRepository officialLeaveRepository;
	
	public void save(OfficialLeave officialLeave){
		officialLeaveRepository.save(officialLeave);
	}
	public void delete(OfficialLeave officialLeave){
		officialLeaveRepository.delete(officialLeave);
	}
	
	public List<OfficialLeave> getAllOfficialLeave(){
		List<OfficialLeave> result = new ArrayList<>();
		officialLeaveRepository.findAll().forEach(res -> result.add(res));
		return result;
	}
	
	public OfficialLeave getLeaveById(long id) {
		OfficialLeave officialLeave = null;
		for(OfficialLeave leave : getAllOfficialLeave()) {
			if(leave.getId() == id)
				officialLeave = leave;
		}
		return officialLeave;
	}
}

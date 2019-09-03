package com.kassem.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kassem.finalproject.model.Applicant;
import com.kassem.finalproject.repository.ApplicantRepository;

@Service
public class ApplicantService {

	@Autowired
	private ApplicantRepository applicantRepository;
	
	public void saveApplicant(Applicant applicant) {
		applicantRepository.save(applicant);
	}
	
	public List<Applicant> getApplicantByOfferId(Long offerId){
		List<Applicant> result = new ArrayList<>();
		for(Applicant applicant : applicantRepository.findAll()) {
			if(applicant.getJobOfferId() == offerId) {
				result.add(applicant);
			}
		}
		return result;
	}
}

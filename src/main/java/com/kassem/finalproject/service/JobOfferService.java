package com.kassem.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kassem.finalproject.model.JobOffer;
import com.kassem.finalproject.repository.JobOfferRepository;

@Service
public class JobOfferService {

	@Autowired
	private JobOfferRepository jobOfferRepository;
	
	public List<JobOffer> getAllOffer(){
		List<JobOffer> offers = new ArrayList<>();
		jobOfferRepository.findAll().forEach(offer -> offers.add(offer));
		return offers;
	}
	
	public void save(JobOffer jobOffer) {
		jobOfferRepository.save(jobOffer);
	}
	
	public void delete(JobOffer jobOffer) {
		jobOfferRepository.delete(jobOffer);
	}
}

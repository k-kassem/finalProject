package com.kassem.finalproject.dataprovider;

import java.util.List;
import java.util.stream.Stream;

import com.kassem.finalproject.model.JobOffer;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;

public class JobOfferDataProvider extends AbstractBackEndDataProvider<JobOffer, CrudFilter>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<JobOffer> data;
	
	public JobOfferDataProvider(List<JobOffer> data) {
		this.data= data;
	}
	@Override
	protected Stream<JobOffer> fetchFromBackEnd(Query<JobOffer, CrudFilter> query) {
		return data.stream();
	}

	@Override
	protected int sizeInBackEnd(Query<JobOffer, CrudFilter> query) {
		return data.size();
	}

	public String getSize() {
		return String.valueOf(data.size());
	}
}

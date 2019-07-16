package com.kassem.finalproject.dataprovider;

import java.util.List;
import java.util.stream.Stream;

import com.kassem.finalproject.model.User;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;

public class UserDataProvider  extends AbstractBackEndDataProvider<User, CrudFilter> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<User> data;
	public UserDataProvider(List<User> data) {
		this.data= data;
	}

	@Override
	protected Stream<User> fetchFromBackEnd(
			Query<User, CrudFilter> query) {
		return data.stream();
	}

	@Override
	protected int sizeInBackEnd(Query<User, CrudFilter> query) {
		return data.size();
	}
	
	public String getSize() {
		return String.valueOf(data.size());
	}
	
	
}

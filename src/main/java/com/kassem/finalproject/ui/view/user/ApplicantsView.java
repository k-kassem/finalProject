package com.kassem.finalproject.ui.view.user;

import javax.annotation.PostConstruct;

import com.kassem.finalproject.ui.secure.MenuBarView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "applicants", layout = MenuBarView.class)
@PageTitle("Applicants")
public class ApplicantsView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void initUi() {
		
		add(new Label("Here All Applicants"));
	}
}

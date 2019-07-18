package com.kassem.finalproject.ui.view.category;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.JobOffer;
import com.kassem.finalproject.service.JobOfferService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("apply")
@PageTitle("Apply Now")
public class ApplyNow extends VerticalLayout  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	JobOfferService jobOfferService;
     
     @PostConstruct
	private void initUi() {
		Label greeting = new Label("Welcome to Our Company..We are Hiring!");
		greeting.getElement().getStyle().set("font-weight", "bold");
		greeting.getElement().getStyle().set("font-size", "150%");
		Label apply = new Label("Please apply to an application...Good Luck :)");
		apply.getElement().getStyle().set("font-weight", "bold");
		apply.getElement().getStyle().set("font-size", "100%");
		
		add(greeting,apply);
		
		// add grid to view
		List<JobOffer> personList = jobOfferService.getAllOffer();
		Grid<JobOffer> grid = new Grid<>();
		grid.setItems(personList);
		grid.addColumn(JobOffer::getJobTitle).setHeader("Job Title");
		grid.addColumn(JobOffer::getSeniorityLevel).setHeader("Seniority Level");
		grid.addColumn(JobOffer::getEmployementType).setHeader("Employement Type");
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
				GridVariant.LUMO_ROW_STRIPES);
		grid.setSelectionMode(SelectionMode.SINGLE);

		add(grid);
		SingleSelect<Grid<JobOffer>, JobOffer> personSelect = grid.asSingleSelect();
		// personSelect can now be used with Binder or HasValue interface
		JobOffer offer = new JobOffer();
		personSelect.addValueChangeListener(e -> {
			JobOffer selectedPerson = e.getValue();
			selectedPerson.copy(selectedPerson,offer);
		});
		Button back = new Button("Back");
		back.setVisible(false);
		back.addClickListener(e ->handleView(grid,getFormAfterSelection(offer)));
		grid.addItemClickListener(
		        event -> view(grid,getFormAfterSelection(offer),back));
		
		
		add(back);
		
	}
     public void view(Grid<JobOffer> grid ,FormLayout form,Button back ) {
    	 remove(grid);
    	 add(form);
    	 back.setVisible(true);
     }
     
     public FormLayout getFormAfterSelection(JobOffer offer) {
		TextField jobTitle = new TextField("JobTitle");
		jobTitle.setValue(offer.getJobTitle() != null ? offer.getJobTitle() :"" );
		
		TextField education = new TextField("Education");
		education.setValue(offer.getEducation() != null ? offer.getEducation() : "");
		
		TextField experience = new TextField("Experience");
		experience.setValue(offer.getExperience() != null ? offer.getExperience() : "");
		
		TextArea description = new TextArea("Description");
		description.setValue(offer.getDescription() != null ? offer.getDescription() : "");
		
		TextField location = new TextField("Location");
		location.setValue(offer.getLocation() != null ? offer.getLocation() : "");
		
		TextField companyName = new TextField("Company Name");
		companyName.setValue(offer.getCompanyName() != null ? offer.getCompanyName() : "");
		
		TextField skills = new TextField("Skills");
		skills.setValue(offer.getSkills() != null ? offer.getSkills() : "");
		
		TextField nbOfApplication = new TextField("Number Of Application");
		nbOfApplication.setValue(offer.getNumberOfApplication() != null ? String.valueOf(offer.getNumberOfApplication()): "");
		
		TextField deadLine = new TextField("Dead Line");
		deadLine.setValue(offer.getDeadLine() != null ? String.valueOf(offer.getDeadLine().toString()) : "");

		TextField employmentTypes = new TextField("Employment Types");
		employmentTypes.setValue(offer.getEmployementType() != null ? offer.getEmployementType() : "");

		TextField seniorityLevel = new TextField("Seniority Level");
		seniorityLevel.setValue(offer.getSeniorityLevel() != null ? offer.getSeniorityLevel() : "");
		Button applyBtn = new Button("Apply");
	    return new FormLayout(companyName, jobTitle,location,education,experience,skills,employmentTypes,seniorityLevel,nbOfApplication,deadLine,description ,applyBtn);
     }
     
     public void handleView(Grid<JobOffer> grid , FormLayout form) {
    	 remove(form);
    	 add(grid);
     }
}

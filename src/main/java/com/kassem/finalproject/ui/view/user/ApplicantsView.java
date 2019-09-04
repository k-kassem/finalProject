package com.kassem.finalproject.ui.view.user;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.Applicant;
import com.kassem.finalproject.model.Applicant.ConnectionWay;
import com.kassem.finalproject.model.Applicant.Degrees;
import com.kassem.finalproject.model.JobOffer;
import com.kassem.finalproject.service.ApplicantService;
import com.kassem.finalproject.service.JobOfferService;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "applicants", layout = MenuBarView.class)
@PageTitle("Applicants")
public class ApplicantsView extends VerticalLayout{

	@Autowired
	private JobOfferService jobOfferService;
	@Autowired
	private ApplicantService applicantService;
	private Applicant selectedPerson = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void initUi() {
		
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
		SingleSelect<Grid<JobOffer>, JobOffer> offerSelected = grid.asSingleSelect();
		// personSelect can now be used with Binder or HasValue interface
		JobOffer offer = new JobOffer();
		offerSelected.addValueChangeListener(e -> {
			JobOffer selectedPerson = e.getValue();
			selectedPerson.copy(selectedPerson,offer);
		});
		/*Button back = new Button("Back");
		back.setVisible(false);
		back.addClickListener(e ->getFormAfterSelection(offer));*/
		grid.addItemClickListener(
		        event -> getApplicantsByOfferDialog(offer).open());
		
		
		//add(back);
	}
	 public Dialog getApplicantsByOfferDialog(JobOffer offer) {
		 List<Applicant> applicantList = applicantService.getApplicantByOfferId(offer.getId());
			Grid<Applicant> grid = new Grid<>();
			grid.setItems(applicantList);
			grid.addColumn(Applicant::getFirstName).setHeader("First Name");
			grid.addColumn(Applicant::getLastName).setHeader("Last Name");
			grid.addColumn(Applicant::getNationality).setHeader("Nationality");
			grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
					GridVariant.LUMO_ROW_STRIPES);
			grid.setSelectionMode(SelectionMode.SINGLE);

			SingleSelect<Grid<Applicant>, Applicant> applicantSelected = grid.asSingleSelect();
			// personSelect can now be used with Binder or HasValue interface
			Applicant applicant1 = new Applicant();
			applicantSelected.addValueChangeListener(e -> {
				selectedPerson = e.getValue();
				//selectedPerson.copy(selectedPerson,applicant1);
			});
			grid.addItemClickListener(
			        event -> getApplicantDialog(selectedPerson).open());
			Dialog dialog = new Dialog();
			VerticalLayout l = new VerticalLayout(grid);
			l.setSizeFull();
			dialog.add(l);
			
			//dialog.add(horizontalLayout);
			//dialog.setCloseOnEsc(false);
			//dialog.setCloseOnOutsideClick(false);
			
			return dialog;
	     }
	 
	private Dialog getApplicantDialog(Applicant applicant) {
		Dialog dialog = new Dialog();
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		 TextField firstName = new TextField("First Name");
    	 firstName.setValue(applicant.getFirstName());
    	 
    	 TextField lastName = new TextField("Last Name");
    	 lastName.setValue(applicant.getLastName());
    	 
    	 TextField nationality = new TextField("Nationality");
    	 nationality.setValue(applicant.getNationality());
    	 
    	 TextField age = new TextField("Age");
    	 age.setValue(String.valueOf(applicant.getAge()));
    	 
    	 TextField email = new TextField("Email");
    	 email.setValue(applicant.getEmail());
    	 
    	 TextField phoneNumber = new TextField("Phone Number");
    	 phoneNumber.setValue(String.valueOf(applicant.getPhoneNumber()));
    	 FormLayout personalInfoLayout = new FormLayout(firstName,lastName,nationality,age,email,phoneNumber);
    	 
    	 //Fields related to education;
    	 TextField degree = new TextField("Degree");
    	 degree.setValue(applicant.getDegree().toString());
    	 
    	 TextField major = new TextField("Major");
    	 major.setValue(applicant.getMajor());
    	 
    	 TextField university = new TextField("University");
    	 university.setValue(applicant.getUniversity());
    	 
    	 DatePicker eduStartDate = new DatePicker("Start Date");
    	 eduStartDate.setValue(applicant.getEduStartDate());
    	 
    	 DatePicker eduEndDate = new DatePicker("End Date");
    	 eduEndDate.setValue(applicant.getEduEndDate());
    	 FormLayout educationLayout = new FormLayout(university,degree,major,eduStartDate,eduEndDate);
    	 
    	 //Field related to experience;
    	 TextField nbOfYear = new TextField("Number Of Year of Experience");
    	 nbOfYear.setValue(String.valueOf(applicant.getNbOfyear()));
    	 
    	 TextField company = new TextField("Compay");
    	 company.setValue(applicant.getCompany());
    	 
    	 TextField role = new TextField("Role");
    	 role.setValue(applicant.getRole());
    	 
    	 TextArea comment = new TextArea("Comment");
    	 comment.setValue(applicant.getComment());
    	 
    	 DatePicker expStartDate = new DatePicker("Start Date");
    	 expStartDate.setValue(applicant.getExpStartDate());
    	 
    	 DatePicker expEndDate = new DatePicker("End Date");
    	 expEndDate.setValue(applicant.getExpEndDate());
    	 FormLayout experienceLayout = new FormLayout(nbOfYear,company,role,comment,expStartDate,expEndDate);
    	 
    	 ComboBox<ConnectionWay> connectionWayCbx = new ComboBox<>("Connecting Way");
		Button sendButton = new Button("Approve", event -> {
			dialog.close();
		});
		Button cancelButton = new Button("Reject", event -> {
			dialog.close();
		});
		horizontalLayout.add(sendButton, cancelButton);
		horizontalLayout.setAlignItems(Alignment.CENTER);
		FormLayout form = new FormLayout(personalInfoLayout,educationLayout,experienceLayout,connectionWayCbx,horizontalLayout);
		dialog.add(form);
		return dialog;
	}
}

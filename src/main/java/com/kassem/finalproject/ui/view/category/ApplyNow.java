package com.kassem.finalproject.ui.view.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.Applicant;
import com.kassem.finalproject.model.Applicant.ConnectionWay;
import com.kassem.finalproject.model.Applicant.Degrees;
import com.kassem.finalproject.model.JobOffer;
import com.kassem.finalproject.model.LeaveRequest;
import com.kassem.finalproject.repository.ApplicantRepository;
import com.kassem.finalproject.service.ApplicantService;
import com.kassem.finalproject.service.JobOfferService;
import com.kassem.finalproject.utils.AppUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
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
	private JobOfferService jobOfferService;
	@Autowired
	private ApplicantService applicantService;
     
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
		/*Button back = new Button("Back");
		back.setVisible(false);
		back.addClickListener(e ->getFormAfterSelection(offer));*/
		grid.addItemClickListener(
		        event -> getFormAfterSelection(offer).open());
		
		
		//add(back);
		
	}
     public void view(Grid<JobOffer> grid ,FormLayout form,Button back ) {
    	 remove(grid);
    	 add(form);
    	 back.setVisible(true);
     }
     
     public Dialog getFormAfterSelection(JobOffer offer) {
    	HorizontalLayout horizontalLayout = new HorizontalLayout();
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
		Dialog dialog = new Dialog();
		Button sendButton = new Button("Apply Now", event -> {
			Dialog d = getDialog(offer.getId());
			d.open();
		    dialog.close();
		});
		Button cancelButton = new Button("Cancel", event -> {
		    dialog.close();
		});
		horizontalLayout.add(sendButton,cancelButton);
		horizontalLayout.setAlignItems(Alignment.CENTER);
		FormLayout form = new FormLayout(companyName, jobTitle,location,education,experience,skills,employmentTypes,seniorityLevel,nbOfApplication,deadLine,description,horizontalLayout );
		dialog.add(form);
		
		//dialog.add(horizontalLayout);
		dialog.setCloseOnEsc(false);
		dialog.setCloseOnOutsideClick(false);
		
		return dialog;
     }
     
     public void handleView(Grid<JobOffer> grid , FormLayout form) {
    	 remove(form);
    	 add(grid);
     }
     
     private Dialog getDialog(Long offerId) {
    	 Dialog dialog = new Dialog();
    	 HorizontalLayout horizontalLayout = new HorizontalLayout();
    	 //field related to personal Information
    	 TextField firstName = new TextField("First Name");
    	 firstName.setRequired(true);
    	 TextField lastName = new TextField("Last Name");
    	 lastName.setRequired(true);
    	 ComboBox<String> nationality = new ComboBox<>("Nationality");
    	 nationality.setItems(getCountries());
    	 nationality.setRequired(true);
    	 TextField age = new TextField("Age");
    	 age.setRequired(true);
    	 TextField email = new TextField("Email");
    	 email.setRequired(true);
    	 TextField phoneNumber = new TextField("Phone Number");
    	 phoneNumber.setRequired(true);
    	 FormLayout personalInfoLayout = new FormLayout(firstName,lastName,nationality,age,email,phoneNumber);
    	 
    	 //Fields related to education;
    	 ComboBox<Degrees> degree = new ComboBox<>("Degree");
    	 degree.setItems(Degrees.values());
    	 TextField major = new TextField("Major");
    	 TextField university = new TextField("University");
    	 DatePicker eduStartDate = new DatePicker("Start Date");
    	 DatePicker eduEndDate = new DatePicker("End Date");
    	 FormLayout educationLayout = new FormLayout(university,degree,major,eduStartDate,eduEndDate);
    	 
    	 //Field related to experience;
    	 TextField nbOfYear = new TextField("Number Of Year of Experience");
    	 TextField company = new TextField("Compay");
    	 TextField role = new TextField("Role");
    	 TextArea comment = new TextArea("Comment");
    	 DatePicker expStartDate = new DatePicker("Start Date");
    	 DatePicker expEndDate = new DatePicker("End Date");
    	 FormLayout experienceLayout = new FormLayout(nbOfYear,company,role,comment,expStartDate,expEndDate);
    	 
    	 ComboBox<ConnectionWay> connectionWayCbx = new ComboBox<>("Connecting Way");
		connectionWayCbx.setItems(ConnectionWay.values());

		Binder<Applicant> binder = new Binder<Applicant>(Applicant.class);
		binder.forField(age).withConverter(new StringToIntegerConverter("Must enter a number"))
				.bind(Applicant::getAge, Applicant::setAge);
		binder.forField(email)
				.withValidator(e_mail -> AppUtils.validateEmail(e_mail), "Email should be like example@example.com")
				.bind(Applicant::getEmail, Applicant::setEmail);
		binder.forField(phoneNumber).withConverter(new StringToIntegerConverter("Must enter a number")).bind(Applicant::getPhoneNumber,
				Applicant::setPhoneNumber);
		binder.forField(nbOfYear).withConverter(new StringToIntegerConverter("Must enter a number")).bind(Applicant::getNbOfyear,
				Applicant::setNbOfyear);

    	 Button sendButton = new Button("Apply", event -> {
    		 saveApplicant(offerId,firstName, lastName,  nationality,  age, email, phoneNumber, degree,major,university,eduStartDate, eduEndDate
    				 ,nbOfYear,  company, role,  comment,expStartDate,  expEndDate,connectionWayCbx);
 		    dialog.close();
 		});
    	 if(firstName.isEmpty()) {
    		 sendButton.setEnabled(false);
    	 }
 		Button cancelButton = new Button("Cancel", event -> {
 		    dialog.close();
 		});
		horizontalLayout.add(sendButton, cancelButton);
		horizontalLayout.setAlignItems(Alignment.CENTER);
		
		Label personalInfoLabel = new Label("Please fill your Personal Information");
		personalInfoLabel.getElement().getStyle().set("font-weight", "bold");
		personalInfoLabel.getElement().getStyle().set("font-size", "100%");
		
		Label educationLabel = new Label("Please Fill your Last Education Information");
		educationLabel.getElement().getStyle().set("font-weight", "bold");
		educationLabel.getElement().getStyle().set("font-size", "100%");
		
		Label experienceLabel = new Label("Please Fill your Last Experience Information");
		experienceLabel.getElement().getStyle().set("font-weight", "bold");
		experienceLabel.getElement().getStyle().set("font-size", "100%");
		
		VerticalLayout vLayout = new VerticalLayout(personalInfoLabel,personalInfoLayout,educationLabel,educationLayout,experienceLabel,experienceLayout,connectionWayCbx,horizontalLayout);
		vLayout.setSizeFull();
		//FormLayout layout = new FormLayout(vLayout);
		dialog.add(vLayout);
		dialog.setCloseOnEsc(false);
		dialog.setCloseOnOutsideClick(false);
		return dialog;
     }
     
	private List<String> getCountries() {
		List<String> result = new ArrayList<>();
		String[] locales = Locale.getISOCountries();
		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);
			result.add(obj.getDisplayCountry());
		}
		return result ;
	}
     
	private void saveApplicant(Long offerId,TextField firstName,TextField lastName,ComboBox<String> nationality,TextField age,TextField email,TextField phoneNumber,ComboBox<Degrees>degree,TextField major,TextField university,DatePicker eduStartDate,DatePicker eduEndDate
			 ,TextField nbOfYear,TextField company,TextField role,TextArea comment,DatePicker expStartDate,DatePicker expEndDate,ComboBox<ConnectionWay>connectionWayCbx) {
		Applicant applicant = new Applicant();
		applicant.setId(Long.valueOf( new Random().nextInt(1000000 + 1)));
		applicant.setJobOfferId(offerId);
		//personal Info Attribute
		applicant.setFirstName(firstName.getValue());
		applicant.setLastName(lastName.getValue());
		applicant.setNationality(nationality.getValue());
		applicant.setAge(Integer.valueOf(age.getValue()));
		applicant.setEmail(email.getValue());
		applicant.setPhoneNumber(Integer.valueOf(phoneNumber.getValue()));
		
		//Education Attributes
		applicant.setDegree(degree.getValue().toString());
		applicant.setMajor(major.getValue());
		applicant.setUniversity(university.getValue());
		applicant.setEduStartDate(eduStartDate.getValue());
		applicant.setEduEndDate(eduEndDate.getValue());
		
		//Experience Attributes
		applicant.setNbOfyear(Integer.valueOf(nbOfYear.getValue()));
		applicant.setCompany(company.getValue());
		applicant.setRole(role.getValue());
		applicant.setComment(comment.getValue());
		applicant.setExpStartDate(expStartDate.getValue());
		applicant.setExpEndDate(expEndDate.getValue());
		
		applicant.setConnectingWay(connectionWayCbx.getValue().toString());
		
		applicantService.saveApplicant(applicant);
	}
}

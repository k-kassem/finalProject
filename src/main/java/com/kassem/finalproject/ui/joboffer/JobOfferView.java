package com.kassem.finalproject.ui.joboffer;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.haijian.Exporter;

import com.kassem.finalproject.dataprovider.JobOfferDataProvider;
import com.kassem.finalproject.dataprovider.UserDataProvider;
import com.kassem.finalproject.model.JobOffer;
import com.kassem.finalproject.model.User;
import com.kassem.finalproject.model.JobOffer.EmploymentTypes;
import com.kassem.finalproject.model.JobOffer.SeniorityLevel;
import com.kassem.finalproject.model.User.Departement;
import com.kassem.finalproject.model.User.RoleValues;
import com.kassem.finalproject.model.User.Status;
import com.kassem.finalproject.service.JobOfferService;
import com.kassem.finalproject.service.UserService;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.kassem.finalproject.utils.AppUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route(value = "newOffer", layout = MenuBarView.class)
public class JobOfferView extends VerticalLayout{

	@Autowired
	JobOfferService jobOfferService;
	@PostConstruct
	private void initUi() {

		List<JobOffer> data = jobOfferService.getAllOffer();
		JobOfferDataProvider jobOfferDataProvider = new JobOfferDataProvider(data);
        setWidth("100%");
        Crud<JobOffer> userCrud = getJobOfferCrud(jobOfferDataProvider);
        add(userCrud);
	}
	
	 private Crud<JobOffer> getJobOfferCrud(JobOfferDataProvider jobOfferDataProvider){
	    	Span footer = new Span();
	    	footer.getElement().getStyle().set("flex", "1");
	    	Button newItemButton = new Button("New Job Offer",new Icon(VaadinIcon.FORWARD));
	    	footer.setText(jobOfferDataProvider.getSize());
	    	// Required attribute to handle the new item action
	    	newItemButton.getElement().setAttribute("new-button", true);

	    	Crud<JobOffer> crud = new Crud<>(JobOffer.class, createPersonEditor());
	    	Anchor anchor = new Anchor(new StreamResource("my-excel.xlsx", Exporter.exportAsExcel(crud.getGrid())), "Download As Excel");
	    	
	    	crud.setToolbar(footer,anchor, newItemButton);
	        crud.getGrid().removeColumnByKey("id");
	        crud.setDataProvider(jobOfferDataProvider);
	        crud.addThemeVariants(CrudVariant.NO_BORDER);
	        crud.addSaveListener(e -> jobOfferService.save(e.getItem()));
	        crud.addDeleteListener(e -> jobOfferService.delete(e.getItem()));
	        return crud;
	    }
	 
	 private CrudEditor<JobOffer> createPersonEditor() {
	        TextField jobTitle = new TextField("JobTitle");
	        TextField education = new TextField("Education");
	        TextField experience = new TextField("Experience");
	        TextArea description = new TextArea("Description");
	        TextField location = new TextField("Location");
	        
	        TextField companyName = new TextField("Company Name");
	        TextField skills = new TextField("Skills");
	        TextField nbOfApplication = new TextField("Number Of Application");
	        DatePicker deadLine = new DatePicker("Dead Line");
	        
	        ComboBox<String> employmentTypesCbx = new ComboBox<String>("EmploymentTypes");
	        employmentTypesCbx.setItems(AppUtils.getStringFromEnum(EmploymentTypes.values()));
	        
	        ComboBox<String> seniorityLevelCbx = new ComboBox<String>("SeniorityLevel");
	        seniorityLevelCbx.setItems(AppUtils.getStringFromEnum(SeniorityLevel.values()));
	        
	        FormLayout form = new FormLayout(companyName, jobTitle,location,education,experience,skills,employmentTypesCbx,seniorityLevelCbx,nbOfApplication,deadLine,description);

	        Binder<JobOffer> binder = new Binder<JobOffer>(JobOffer.class);
	        binder.bind(companyName,JobOffer::getCompanyName ,JobOffer::setCompanyName);
	        binder.bind(jobTitle, JobOffer::getJobTitle ,JobOffer::setJobTitle);
	        
			binder.bind(location,JobOffer::getLocation ,JobOffer::setLocation);
	        binder.bind(skills, JobOffer::getSkills ,JobOffer::setSkills);
	        binder.forField(nbOfApplication)
	        .withConverter(
	                new StringToIntegerConverter("Must enter a number"))
	        .bind(JobOffer::getNumberOfApplication ,JobOffer::setNumberOfApplication);
	        
	        binder.bind(employmentTypesCbx, JobOffer::getEmployementType ,JobOffer::setEmployementType);
	        binder.bind(seniorityLevelCbx, JobOffer::getSeniorityLevel ,JobOffer::setSeniorityLevel);
	        binder.bind(description, JobOffer::getDescription ,JobOffer::setDescription);
	        binder.bind(education, JobOffer::getEducation ,JobOffer::setEducation);
	        binder.bind(experience, JobOffer::getExperience ,JobOffer::setExperience);
	        binder.bind(deadLine, JobOffer::getDeadLine ,JobOffer::setDeadLine);
	        
	        return new BinderCrudEditor<>(binder, form);
	    }
}

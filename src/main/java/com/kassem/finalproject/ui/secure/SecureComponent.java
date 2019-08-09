package com.kassem.finalproject.ui.secure;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.User;
import com.kassem.finalproject.service.UserService;
import com.kassem.finalproject.ui.login.SessionInfo;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLayout;

@RoutePrefix("secure")
public class SecureComponent extends VerticalLayout implements RouterLayout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired 
	UserService userService;
	
	@Autowired
	SessionInfo sessionInfo;

	@PostConstruct
	public void init() {
        /*setSizeFull();
        Label loginLabel = new Label("Secure application. Welcome " + sessionInfo.getCurrentUser().getUsername() + "!");
        loginLabel.getElement().getStyle().set("font-weight", "bold");
        loginLabel.getElement().getStyle().set("font-size", "150%");

        add(loginLabel);*/
		User user = userService.getUserById(sessionInfo.getCurrentUser().getId()); 
		 Label loginLabel = new Label("Secure application. Welcome " + user.getId() + "!");
	        loginLabel.getElement().getStyle().set("font-weight", "bold");
		/*TextField jobTitle = new TextField("JobTitle");
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
		skills.setValue(offer.getSkills() != null ? offer.getSkills() : "");*/
    }

}
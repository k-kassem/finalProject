package com.kassem.finalproject.ui.secure;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.User;
import com.kassem.finalproject.service.UserService;
import com.kassem.finalproject.ui.login.SessionInfo;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLayout;

@Route("changepassword")
public class ChangePasword extends VerticalLayout implements RouterLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SessionInfo sessionInfo;
	private Button button;
	private VerticalLayout layout;
	private User user;
	private Button submit ;
	
	@PostConstruct
	public void init() {
		setAlignItems(Alignment.CENTER);
        
		user = userService.getUserById(sessionInfo.getCurrentUser().getId()); 
		 Label loginLabel = new Label(" Welcome " + user.getFirstName() + " please change your password"); 
	        //loginLabel.getElement().getStyle().set("font-weight", "bold");
	        //this.add(loginLabel);
	        layout = new VerticalLayout(loginLabel);
	        Label oldPasswordLbl = new Label ("Old Password");
	        oldPasswordLbl.setSizeFull();
	       
	        Label newPasswordLbl = new Label("New Password");
	        Label confirmPasswordLbl = new Label("Confirm Paswword");
	        PasswordField oldPaswordTxt = new PasswordField();
	        PasswordField newPasswordTxt = new PasswordField();
	        PasswordField confirmPasswordTxt = new PasswordField();
	        
	        HorizontalLayout oldPwdHLayout = new HorizontalLayout(oldPasswordLbl,oldPaswordTxt);
	        HorizontalLayout newPwdHLayout = new HorizontalLayout(newPasswordLbl,newPasswordTxt);
	        HorizontalLayout confirmPwdHLayout = new HorizontalLayout(confirmPasswordLbl,confirmPasswordTxt);
	        
	        layout.add(oldPwdHLayout,newPwdHLayout,confirmPwdHLayout);
	        
	        //add binder for each field to validate the input
	        Binder<User> binder = new Binder<User>(User.class);
	         binder.forField(oldPaswordTxt)
	 		.withValidator(oldPasword -> isSameOldPassword(oldPaswordTxt.getValue(),user), "Wrong Old Password")
	 		.bind(User::getPassword, User::setPassword);
	         
	         binder.forField(newPasswordTxt)
		 		.withValidator(newPassword -> !isNewAndPasswordAreSame(oldPaswordTxt.getValue(),newPasswordTxt.getValue()), "Old Password and New password should not be same")
		 		.bind(User::getPassword, User::setPassword);
		         
	         
	         binder.forField(confirmPasswordTxt)
		 		.withValidator(confirmPassword -> isNewPasswordAreSame(newPasswordTxt.getValue(),confirmPasswordTxt.getValue()), "The new Password is not Valid")
		 		.bind(User::getPassword, User::setPassword);
		         
	        HorizontalLayout hlayout = new HorizontalLayout(/*img1,*/layout);
	        hlayout.setBoxSizing(BoxSizing.BORDER_BOX);
	        
	        //add button and for update user
	        submit =  new Button("Submit", event -> {
	        	String newPassword = new String();
	        	
	        	newPassword = newPasswordTxt.getValue();
	        	
	        	changePassword(newPassword,user);
	        	layout.add(new Label("Please re-login with the new Password"));
	        	
	        });
	        submit.setIcon(new Icon(VaadinIcon.PASSWORD));
	        submit.setSizeFull();
	        submit.setHeight("75");
	        submit.setEnabled(false);
	        
	        
	        layout.add(submit);
	        add(hlayout);
	      }
	protected void onAttach(AttachEvent attachEvent) {
        UI ui = getUI().get();
        
        button = new Button("Logout", event -> {
        	//doProcessAfterLogout(String.valueOf(session.getCurrentUser().getId()));
            // Redirect this page immediately
            ui.getPage().executeJavaScript(
                    "window.location.href='/logout'");

            // Close the session
            ui.getSession().close();
        });

        button.setIcon(new Icon(VaadinIcon.EXIT));
        button.setSizeFull();
        button.setHeight("75");
        layout.add(button);

        // Notice quickly if other UIs are closed
        ui.setPollInterval(3000);
    }
	
	private void changePassword(String oldPwd,User user) {
		user.setPassword(oldPwd);
		user.setFirstLogin(0);
		userService.UpdatePassword(user);
		submit.setVisible(false);
	}
	private Boolean isSameOldPassword(String oldPwd,User user) {
		if(oldPwd.equals(user.getPassword())) {
			
			return true;
			
		}
		return false;
				
	}
	
	private Boolean isNewPasswordAreSame(String newPwd,String confirmPwd) {
		if(newPwd.equals(confirmPwd)) {
			submit.setEnabled(true);
			return true;
		}
		return false;
	}
	
	private Boolean isNewAndPasswordAreSame(String oldPwd,String newPwd) {
		if(oldPwd.equals(newPwd)) {
			//submit.setEnabled(true);
			return true;
		}
		return false;
	}
}

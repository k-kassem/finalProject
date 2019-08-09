package com.kassem.finalproject.ui.secure;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.User;
import com.kassem.finalproject.model.User.Status;
import com.kassem.finalproject.service.UserService;
import com.kassem.finalproject.ui.login.SessionInfo;
import com.kassem.finalproject.utils.AppUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MenuBarView.class)
@PageTitle("Home Page")
public class SecureView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired 
	UserService userService;
	
	@Autowired
	SessionInfo sessionInfo;
	
	TextField userNameTxt;
	TextField lastNameTxt;
	TextField firstNameTxt;
	TextArea addressTxt;
	TextField passwordTxt;
	TextField emailTxt;
	TextField ageTxt;
	ComboBox<String> statusCbx;
	TextField departementTxt;
	TextField salaryTxt;
	TextField startDateTxt;
	TextField positionTxt;
	
	@PostConstruct
	public void init() {
		User user = userService.getUserById(sessionInfo.getCurrentUser().getId());
		Label loginLabel = new Label("Welcome Back " + user.getUsername() + "!");
        loginLabel.getElement().getStyle().set("font-weight", "bold");
        loginLabel.getElement().getStyle().set("font-size", "150%");
		VerticalLayout layout = new VerticalLayout();
		
		userNameTxt = new TextField("User Name");
		userNameTxt.setValue(user.getUsername() != null ? user.getUsername() :"" );
		//userNameTxt.setEnabled(false);
		
		firstNameTxt = new TextField("First Name");
		firstNameTxt.setValue(user.getFirstName() != null ? user.getFirstName() : "");
		
		lastNameTxt = new TextField("Last Name");
		lastNameTxt.setValue(user.getLastName() != null ? user.getLastName() : "");
		
		addressTxt = new TextArea("Address");
		addressTxt.setValue(user.getAddress() != null ? user.getAddress() : "");
		
		passwordTxt = new TextField("Password");
		passwordTxt.setValue(user.getPassword() != null ? user.getPassword() : "");
		
		emailTxt = new TextField("Email");
		emailTxt.setValue(user.getEmail() != null ? user.getEmail() : "");
		
		ageTxt = new TextField("Age");
		ageTxt.setValue(user.getAge() != null ? String.valueOf(user.getAge()) : "");
		//ageTxt.setEnabled(false);
		
		statusCbx = new ComboBox<String>("Status");
        statusCbx.setItems(AppUtils.getStringFromEnum(Status.values()));
        statusCbx.setValue(user.getStatus());
		
		departementTxt = new TextField("Departement");
		departementTxt.setValue(user.getDepartement() != null ? user.getDepartement() : "");
		departementTxt.setEnabled(false);
		
		salaryTxt = new TextField("Salary");
		salaryTxt.setValue(user.getSalary() != null ? String.valueOf(user.getSalary()) : "");
		//salaryTxt.setEnabled(false);
		
		startDateTxt = new TextField("Start Date");
		startDateTxt.setValue(user.getStartDate() != null ? user.getStartDate().toString() : "");
		//startDateTxt.setEnabled(false);
		
		positionTxt = new TextField("Position");
		positionTxt.setValue(user.getPosition() != null ? user.getPosition() :"" );
		
		
		FormLayout formLayout = new FormLayout(userNameTxt,passwordTxt,firstNameTxt,lastNameTxt,addressTxt,emailTxt,ageTxt,
				statusCbx,departementTxt,positionTxt,salaryTxt,startDateTxt);
		String firstName = firstNameTxt.getValue();
		String lastName = lastNameTxt.getValue();
		String password = passwordTxt.getValue();
		String email = emailTxt.getValue();
		String address = addressTxt.getValue();
		String status = statusCbx.getValue();
		Button editBtn = new Button("Edit");
		editBtn.addClickListener(e -> edit(user.getId(),firstName,lastName,email,address,password,status));
		layout.add(loginLabel,formLayout,editBtn);
		add(layout);
	}
	
	public void edit(Long Id,String firstName,String lastName,String email,String address,String password,String status) {
		
		String message = userService.updateUser(Id,firstName,lastName,email,address,password,status);
		Label afterEdit = new Label(message);
		add(afterEdit);
	}
}
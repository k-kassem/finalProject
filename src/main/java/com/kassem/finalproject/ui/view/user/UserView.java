package com.kassem.finalproject.ui.view.user;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.haijian.Exporter;

import com.kassem.finalproject.dataprovider.UserDataProvider;
import com.kassem.finalproject.model.User;
import com.kassem.finalproject.model.User.Departement;
import com.kassem.finalproject.model.User.RoleValues;
import com.kassem.finalproject.model.User.Status;
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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route(value = "user", layout = MenuBarView.class)
public class UserView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	UserService userService;

	@PostConstruct
	private void initUi() {

		List<User> data = userService.getAllUser();
		UserDataProvider userDataProvider = new UserDataProvider(data);
        setWidth("100%");
        Crud<User> userCrud = getUserCrud(userDataProvider);
        add(userCrud);
	}

	private CrudEditor<User> createPersonEditor() {
        TextField firstName = new TextField("User Name");
        PasswordField password = new PasswordField("Password");
        TextField emailField = new TextField("Email");
        
        ComboBox<String> roleCbx = new ComboBox<String>("Role");
        roleCbx.setItems(AppUtils.getStringFromEnum(RoleValues.values()));
        
        ComboBox<String> statusCbx = new ComboBox<String>("Status");
        statusCbx.setItems(AppUtils.getStringFromEnum(Status.values()));
        
        ComboBox<String> deptCbx = new ComboBox<String>("Departement");
        deptCbx.setItems(AppUtils.getStringFromEnum(Departement.values()));
        
        TextField ageField = new TextField("Age");
        
        TextField salaryField = new TextField("Salary");
        
        DatePicker datePicker = new DatePicker("Start Date"); 
        FormLayout form = new FormLayout(firstName, password,emailField,ageField,roleCbx,statusCbx,deptCbx,salaryField,datePicker);

       
        Binder<User> binder = new Binder<User>(User.class);
        binder.bind(firstName,User::getUsername ,User::setUserName);
        binder.bind(password, User::getPassword, User::setPassword);
        
		binder.forField(emailField)
				.withValidator(email -> AppUtils.validateEmail(email), "Email should be like example@example.com")
				.bind(User::getEmail, User::setEmail);
        binder.bind(roleCbx, User::getRole, User::setRole);
        binder.forField(ageField)
        .withConverter(
                new StringToIntegerConverter("Must enter a number"))
        .bind(User::getAge, User::setAge);
        
        binder.bind(statusCbx, User::getStatus, User::setStatus);
        binder.bind(deptCbx, User::getDepartement, User::setDepartement);
        binder.forField(salaryField)
        .withConverter(
                new StringToIntegerConverter("Must enter a number"))
        .bind(User::getSalary, User::setSalary);
        binder.bind(datePicker, User::getStartDate, User::setStartDate);
        
        return new BinderCrudEditor<>(binder, form);
    }
    
    private void deleteUser(User user,Crud<User> crud,UserDataProvider userDataProvider) {
    	userService.delete(user);
    	UserDataProvider userDataProvider2 = new  UserDataProvider(new ArrayList<>());
    	//crud.getDataProvider().refreshAll();
    	crud.setDataProvider(userDataProvider2);
    	crud.setDataProvider(userDataProvider);
    }
    
    private Crud<User> getUserCrud(UserDataProvider userDataProvider){
    	Span footer = new Span();
    	footer.getElement().getStyle().set("flex", "1");
    	Button newItemButton = new Button("Add person",new Icon(VaadinIcon.FORWARD));
    	footer.setText(userDataProvider.getSize());
    	// Required attribute to handle the new item action
    	newItemButton.getElement().setAttribute("new-button", true);

    	Crud<User> crud = new Crud<>(User.class, createPersonEditor());
    	Anchor anchor = new Anchor(new StreamResource("my-excel.xlsx", Exporter.exportAsExcel(crud.getGrid())), "Download As Excel");
    	
    	crud.setToolbar(footer,anchor, newItemButton);
        crud.getGrid().removeColumnByKey("id");
        crud.setDataProvider(userDataProvider);
        crud.addThemeVariants(CrudVariant.NO_BORDER);
        crud.addSaveListener(e -> userService.save(e.getItem()));
        crud.addDeleteListener(e -> deleteUser(e.getItem(),crud,userDataProvider));
        return crud;
    }
}
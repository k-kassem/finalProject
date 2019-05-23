package com.kassem.finalproject.ui.view.category;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.service.UserService;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "category", layout = MenuBarView.class)
public class CategoryView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	UserService userService;
	@PostConstruct
	private void initUI() {

		/*List<User> data = userService.getAllUser();
		UserDataProvider userDataProvider = new UserDataProvider(data);
        setWidth("100%");
        Crud<User> userCrud = getUserCrud(userDataProvider);*/
        add(new Label("TODO"));
	}
	
    /*private CrudEditor<User> createPersonEditor() {
        TextField firstName = new TextField("User Name");
        PasswordField password = new PasswordField("Password");
        TextField emailField = new TextField("Email");
        ComboBox<String> roleCbx = new ComboBox<String>("Role");
        roleCbx.setItems(getStringFromEnum(RoleValues.values()));
        FormLayout form = new FormLayout(firstName, password,emailField,roleCbx);

        Binder<User> binder = new Binder<User>(User.class);
        binder.bind(firstName,User::getUsername ,User::setUserName);
        binder.bind(password, User::getPassword, User::setPassword);
        binder.bind(emailField, User::getEmail, User::setEmail);
        binder.bind(roleCbx, User::getRole, User::setRole);
        
        return new BinderCrudEditor<>(binder, form);
    }
    private List<String> getStringFromEnum(RoleValues[] roles){
    	List<String> roleStr = new ArrayList<>();
    	for(RoleValues role : roles) {
    		roleStr.add(role.toString());
    	}
    	return roleStr;
    }
    private void deleteUser(User user,Crud<User> crud,UserDataProvider userDataProvider) {
    	userService.delete(user);
    	UserDataProvider userDataProvider2 = new  UserDataProvider(new ArrayList<>());
    	//crud.getDataProvider().refreshAll();
    	crud.setDataProvider(userDataProvider2);
    	crud.setDataProvider(userDataProvider);
    }
    
    private Crud<User> getUserCrud(UserDataProvider userDataProvider){
    	Crud<User> crud = new Crud<>(User.class, createPersonEditor());
        crud.getGrid().removeColumnByKey("id");
        crud.setDataProvider(userDataProvider);
        crud.addThemeVariants(CrudVariant.NO_BORDER);
        crud.addSaveListener(e -> userService.save(e.getItem()));
        crud.addDeleteListener(e -> deleteUser(e.getItem(),crud,userDataProvider));
        return crud;
    }*/
}
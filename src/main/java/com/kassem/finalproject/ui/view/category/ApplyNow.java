package com.kassem.finalproject.ui.view.category;

import javax.annotation.PostConstruct;

import com.kassem.finalproject.model.User;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;

@Route("apply")
public class ApplyNow extends VerticalLayout  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 TextField ageField = new TextField("Age");
     
     TextField salaryField = new TextField("Salary");
     
     @PostConstruct
 	private void initUi() {
    	 Binder<User> binder = new Binder<User>(User.class);
    	 FormLayout form = new FormLayout(ageField,salaryField);
    	 form.add(ageField);
    	 add(form);
    	 binder.forField(ageField)
         .withConverter(
                 new StringToIntegerConverter("Must enter a number"))
         .bind(User::getAge, User::setAge);
     }

}

package com.kassem.finalproject.ui.view.user;

import java.time.LocalDate;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.LeaveRequest;
import com.kassem.finalproject.model.LeaveRequest.LeaveType;
import com.kassem.finalproject.model.User;
import com.kassem.finalproject.model.User.Position;
import com.kassem.finalproject.service.LeaveRequestService;
import com.kassem.finalproject.service.UserService;
import com.kassem.finalproject.ui.login.SessionInfo;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.kassem.finalproject.utils.AppUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "leavepermission", layout = MenuBarView.class)
@PageTitle("Leave Permission")
public class LeaveRequestView extends VerticalLayout{

	@Autowired 
	private UserService userService;
	
	@Autowired
	private SessionInfo sessionInfo;
	
	@Autowired
	private LeaveRequestService leaveRequestService;
	/**
	 * 
	 */
	private TextField usernameTxt;
	private TextField lastTxt;
	private TextField departmentTxt;
	private TextField addressDuringLeaveTxt;
	private TextField numberOfDaysTxt;
	private DatePicker startDateField; 
	private DatePicker endDateField; 
	private ComboBox<String> leaveTypeCbx ;
	private Button saveBtn;
	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	public void initUi() {
		User user = userService.getUserById(sessionInfo.getCurrentUser().getId());
		Label loginLabel = new Label("Leave Permission");
        loginLabel.getElement().getStyle().set("font-weight", "bold");
        loginLabel.getElement().getStyle().set("font-size", "150%");
        init(user);
        FormLayout from = getLeaveForm();
        add(loginLabel,from);
	}

	private void init(User user) {
		usernameTxt = new TextField("Employee Name");
		usernameTxt.setValue(user.getFirstName());

		lastTxt = new TextField("Last Name");
		lastTxt.setValue(user.getLastName());

		departmentTxt = new TextField("Department");
		departmentTxt.setValue(user.getDepartement());

		addressDuringLeaveTxt = new TextField("Address During Leave");

		numberOfDaysTxt = new TextField("Number Of Days");

		startDateField = new DatePicker("Leave Start");

		endDateField = new DatePicker("Leave End");

		leaveTypeCbx = new ComboBox<String>("Leave Type");
		leaveTypeCbx.setItems(AppUtils.getStringFromEnum(LeaveType.values()));
		saveBtn = new Button("Send");
		saveBtn.addClickListener(e -> {
			save();
		});
		Binder<LeaveRequest> binder = new Binder<LeaveRequest>(LeaveRequest.class);
		binder.forField(numberOfDaysTxt).withConverter(new StringToIntegerConverter("Must enter a number"))
				.bind(LeaveRequest::getNbOfDays, LeaveRequest::setNbOfDays);
		binder.forField(addressDuringLeaveTxt)
		.withValidator(email -> AppUtils.validateEmail(email), "Email should be like example@example.com")
		.bind(LeaveRequest::getAddressDuringLeave, LeaveRequest::setAddressDuringLeave);
	}
	
	private FormLayout getLeaveForm(){
		return new FormLayout(usernameTxt,lastTxt,departmentTxt,addressDuringLeaveTxt,leaveTypeCbx,numberOfDaysTxt,startDateField,endDateField,saveBtn);  
	}
	private void save(){
		String message = "";
		Dialog d = new Dialog();
		LeaveRequest leave = new LeaveRequest();
		String username = usernameTxt.getValue();
		String lastName = lastTxt.getValue();
		String dept = departmentTxt.getValue();
		String address = addressDuringLeaveTxt.getValue();
		Integer nbOfDay = Integer.valueOf(numberOfDaysTxt.getValue());
		LocalDate startDate = startDateField.getValue();
		LocalDate endDate = endDateField.getValue();
		String leaveType = leaveTypeCbx.getValue();
		LocalDate now = LocalDate.now();
		if(now.compareTo(startDate)>0) {
			message = "Date should be after now";
			d.add(new Label(message));
			d.open();
			return ;
		}
		if(startDate.compareTo(endDate)>0) {
			message = "Start Date should be after End date";
			d.add(new Label(message));
			d.open();
			return ;
		}
		if(daysBetween(startDate,endDate) > nbOfDay) {
			message = "Start Date end date should be less then number of days";
			d.add(new Label(message));
			d.open();
			return ;
		}
			
		leave.setId(Long.valueOf( new Random().nextInt(1000000 + 1)));
		leave.setUserid(String.valueOf(sessionInfo.getCurrentUser().getId()));
		leave.setUserName(username);
		leave.setUserLastName(lastName);
		leave.setDepartement(dept);
		leave.setAddressDuringLeave(address);
		leave.setNbOfDays(nbOfDay);
		leave.setStartDate(startDate);
		leave.setEndDate(endDate);
		leave.setLeaveType(leaveType);
		leaveRequestService.save(leave);
		d.add(new Label("Your leave request has successfully sent to manager"));
		d.open();
	}

	private int daysBetween(LocalDate d1, LocalDate d2) {
        return d2.getDayOfMonth() - d1.getDayOfMonth();
    }
}

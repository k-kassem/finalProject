package com.kassem.finalproject.ui.view.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.LeaveRequest;
import com.kassem.finalproject.model.Message;
import com.kassem.finalproject.model.User;
import com.kassem.finalproject.service.LeaveRequestService;
import com.kassem.finalproject.service.MessageService;
import com.kassem.finalproject.service.UserService;
import com.kassem.finalproject.ui.login.SessionInfo;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "allleaves", layout = MenuBarView.class)
@PageTitle("All Leaves")
public class LeavesView extends VerticalLayout{

	@Autowired
	private LeaveRequestService leaveService; 
	@Autowired
	private MessageService messageService; 
	@Autowired
	private UserService userService;
	
	private SessionInfo session;
	
	@PostConstruct
	public void initUi() {
		//User user = userService.getUserById(sessionInfo.getCurrentUser().getId());
		session = new SessionInfo();
		Label loginLabel = new Label("All Leaves");
        loginLabel.getElement().getStyle().set("font-weight", "bold");
        loginLabel.getElement().getStyle().set("font-size", "150%");
        add(loginLabel);
        
     // add grid to view
     		List<LeaveRequest> personList = leaveService.getAllLeaves();
     		Grid<LeaveRequest> grid = new Grid<>();
     		grid.setItems(personList);
     		grid.addColumn(LeaveRequest::getUserName).setHeader("Employee Name");
     		grid.addColumn(LeaveRequest::getStartDate).setHeader("Start Date");
     		grid.addColumn(LeaveRequest::getEndDate).setHeader("End Date");
     		grid.addColumn(LeaveRequest::getNbOfDays).setHeader("Number Of Days");
     		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
     				GridVariant.LUMO_ROW_STRIPES);
     		grid.setSelectionMode(SelectionMode.SINGLE);

     		add(grid);
     		SingleSelect<Grid<LeaveRequest>, LeaveRequest> personSelect = grid.asSingleSelect();
     		// personSelect can now be used with Binder or HasValue interface
     		LeaveRequest offer = new LeaveRequest();
     		personSelect.addValueChangeListener(e -> {
     			LeaveRequest selectedItem = e.getValue();
     			selectedItem.copy(selectedItem,offer);
     		});
     		
     		grid.addItemClickListener(
     		        event -> getFormAfterSelection(offer).open());
     		
	}
	public Dialog getFormAfterSelection(LeaveRequest leave) {
    	HorizontalLayout horizontalLayout = new HorizontalLayout();
    	Label loginLabel = new Label(leave.getUserName()+" wants Leave with "+leave.getNbOfDays()+" days from "+leave.getStartDate()+" to "+leave.getEndDate());
        loginLabel.getElement().getStyle().set("font-weight", "bold");
        loginLabel.getElement().getStyle().set("font-size", "150%");
		Dialog dialog = new Dialog();
		Button sendButton = new Button("Approve", event -> {
			sendMessage(leave,"Approve Leave","Your Leave Has Been Approved By Manager");
			dialog.close();
		});
		Button cancelButton = new Button("Reject", event -> {
		    sendMessage(leave,"Reject Leave","Your Leave Has Been Rejected By Manager");
		    dialog.close();
		});
		horizontalLayout.add(sendButton,cancelButton);
		horizontalLayout.setAlignItems(Alignment.CENTER);
		FormLayout form = new FormLayout(horizontalLayout );
		dialog.add(loginLabel,form);
		
		//dialog.add(horizontalLayout);
		dialog.setCloseOnEsc(false);
		dialog.setCloseOnOutsideClick(false);
		
		return dialog;
     }
	
	private void sendMessage(LeaveRequest leaveRequest,String title,String context){
		Message message = new Message();
		User user = userService.getUserById(Long.valueOf(leaveRequest.getUserid()));
		message.setId(Long.valueOf( new Random().nextInt(1000000 + 1)));
		message.setFromUserId(String.valueOf(session.getCurrentUser().getId()));
		message.setToUserId(leaveRequest.getUserid());
		message.setTitle(title);
		message.setContext(context);
		message.setSentDate(LocalDate.now());
		user.setNbOfLeaves(user.getNbOfLeaves()-leaveRequest.getNbOfDays());
		messageService.saveMessage(message);
		userService.save(user);
		leaveService.delete(leaveRequest);
	}
}

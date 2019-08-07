package com.kassem.finalproject.ui.view.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.klaudeta.PaginatedGrid;

import com.kassem.finalproject.model.DailyAttend;
import com.kassem.finalproject.model.User;
import com.kassem.finalproject.service.DailyAttendService;
import com.kassem.finalproject.service.UserService;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.GridContextMenu;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "attendance", layout = MenuBarView.class)
@PageTitle("Daily Attendance")
public class AttendanceView extends VerticalLayout{

	@Autowired
	DailyAttendService attendService;
	@Autowired
	UserService userService;
	
	List<DailyAttend> attendance;

	@PostConstruct
	public void initUi() {
		attendance = attendService.getAllattended();
		Label l = new Label();
		PaginatedGrid<DailyAttend> grid = getGrid(attendance, l);
		/*Button button = new Button("To Home");
		button.addClickListener(e -> {
			ask();
		});*/
		// Create a grid bound to the list

		add(grid,/* button,*/ l/*, hLayout()*/);
	}
	/*public VerticalLayout hLayout(){
		VerticalLayout p = new VerticalLayout();
		p.setSpacing(false);
		p.setWidth("600px");
		p.setHeight("50px");

		Button ad = new Button("test",new Icon(VaadinIcon.AREA_SELECT));
		ad.setSizeFull();
		ad.setHeight("75");
		//ad.getElement().setAttribute("theme", "primary");
		ad.getElement().setAttribute("background-color", "red");
		Button de = new Button("test",new Icon(VaadinIcon.VIEWPORT));
		de.setSizeFull();
		de.setHeight("75");
		Button ed = new Button("test",new Icon(VaadinIcon.TRENDIND_DOWN));
		ed.setSizeFull();
		ed.setHeight("75");

		p.add(ad, ed, de);
		return p;
	}*/
	private Dialog getDialogOnClick(DailyAttend attend){
		Dialog dialog = new Dialog();
		User user = userService.getUserById(Long.valueOf(attend.getUserId()));
		TextField nametxt = new TextField("Name");
		nametxt.setValue(user.getFirstName());
		TextField lastNametxt = new TextField("Last Name");
		lastNametxt.setValue(user.getLastName());
		
		TextField emailtxt = new TextField("Email");
		lastNametxt.setValue(user.getEmail());
		
		dialog.add(nametxt);
		dialog.add(lastNametxt);
		dialog.add(emailtxt);
		dialog.setCloseOnEsc(true);
		dialog.setCloseOnOutsideClick(true);
		
		return dialog;
	}

	private PaginatedGrid<DailyAttend> getGrid(List<DailyAttend> attend, Label l) {
		PaginatedGrid<DailyAttend> grid = new PaginatedGrid<>();
		grid.setPageSize(4);
		grid.setPaginatorSize(5);
		grid.setItems(attend);
		grid.addColumn(DailyAttend::getUserName).setHeader("User Name");
		grid.addColumn(DailyAttend::getStartTime).setHeader("Present At");
		grid.addColumn(DailyAttend::getDate).setHeader("Date ");
		GridContextMenu<DailyAttend> contextMenu = grid.addContextMenu();
		contextMenu.addItem("Show name of context menu target item", e -> {
			//String name = e.getItem().map(Person::getName).orElse("no target item");
			DailyAttend per = e.getItem().get();
			getDialogOnClick(per).open();
		});
		contextMenu.addItem("Send Message", e -> {
			DailyAttend per = e.getItem().get();
			getMessageDialog(per,l).open();
		});
		return grid;
	}
	private Dialog getMessageDialog(DailyAttend attend, Label l){
		VerticalLayout layout = new VerticalLayout();
		User user = userService.getUserById(Long.valueOf(attend.getUserId()));
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		
		Dialog dialog = new Dialog();
		TextField nametxt = new TextField("Name");
		nametxt.setValue(user.getFirstName());
		TextArea message = new TextArea("Message");
		message.setValue(user.getLastName());
		layout.add(nametxt,message);
		dialog.add(layout);
		Button sendButton = new Button("Confirm", event -> {
			l.setText(message.getValue());
		    dialog.close();
		});
		Button cancelButton = new Button("Cancel", event -> {
		    l.setText("Cancelled...");
		    dialog.close();
		});
		horizontalLayout.add(sendButton,cancelButton);
		dialog.add(horizontalLayout);
		dialog.setCloseOnEsc(false);
		dialog.setCloseOnOutsideClick(false);
		
		return dialog;
	}
}

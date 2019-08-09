package com.kassem.finalproject.ui.view.user;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.klaudeta.PaginatedGrid;

import com.kassem.finalproject.model.MonthlyAttend;
import com.kassem.finalproject.model.User.RoleValues;
import com.kassem.finalproject.service.MonthlyAttendService;
import com.kassem.finalproject.ui.login.SessionInfo;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "monthyreport", layout = MenuBarView.class)
@PageTitle("Monthly Report")
public class MonthlyReportView extends VerticalLayout{

	@Autowired
	private MonthlyAttendService monthlyAttendService;
	private List<MonthlyAttend> monthlyAttends;
	private SessionInfo session;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@PostConstruct
	public void initUi() {
		Label l = new Label("Here is Monthly Report");
		session = new SessionInfo();
		PaginatedGrid<MonthlyAttend> grid = new PaginatedGrid<>();
		if(session.getCurrentUser().getRole().equalsIgnoreCase(RoleValues.Admin.toString())){
			monthlyAttends = monthlyAttendService.getAllRecord();
			grid = getGlobalReportGrid(monthlyAttends,l,true); 
		}
		else{
			monthlyAttends = monthlyAttendService.getMonthlyAttendByUserId(Long.valueOf(session.getCurrentUser().getId()));
			grid = getGlobalReportGrid(monthlyAttends,l,false); 
		}
		add(grid,l);
	}
	
	private PaginatedGrid<MonthlyAttend> getGlobalReportGrid(List<MonthlyAttend> attend, Label l,Boolean showName) {
		PaginatedGrid<MonthlyAttend> grid = new PaginatedGrid<>();
		grid.setPageSize(15);
		grid.setPaginatorSize(5);
		grid.setItems(attend);
		if (showName) {
			grid.addColumn(MonthlyAttend::getFirstName).setHeader("First Name");
			grid.addColumn(MonthlyAttend::getLastName).setHeader("Last Name");
		}
		grid.addColumn(MonthlyAttend::getStartTime).setHeader("Start Time");
		grid.addColumn(MonthlyAttend::getEndTime).setHeader("End Time");
		grid.addColumn(MonthlyAttend::getNbOfHour).setHeader("Number Of Hour");
		grid.addColumn(MonthlyAttend::getDate).setHeader("Date");
		/*GridContextMenu<DailyAttend> contextMenu = grid.addContextMenu();
		contextMenu.addItem("Show name of context menu target item", e -> {
			//String name = e.getItem().map(Person::getName).orElse("no target item");
			DailyAttend per = e.getItem().get();
			getDialogOnClick(per).open();
		});
		contextMenu.addItem("Send Message", e -> {
			DailyAttend per = e.getItem().get();
			getMessageDialog(per,l).open();
		});*/
		return grid;
	}
}

package com.kassem.finalproject.ui.view.user;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.klaudeta.PaginatedGrid;

import com.kassem.finalproject.model.MonthlyAttend;
import com.kassem.finalproject.model.User.RoleValues;
import com.kassem.finalproject.service.MonthlyAttendService;
import com.kassem.finalproject.ui.login.SessionInfo;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
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
		Label l = new Label();
        l.getElement().getStyle().set("font-weight", "bold");
        l.getElement().getStyle().set("font-size", "150%");
		session = new SessionInfo();
		Grid<MonthlyAttend> grid = new Grid<>();
		if(session.getCurrentUser().getRole().equalsIgnoreCase(RoleValues.Admin.toString())){
			monthlyAttends = monthlyAttendService.getAllRecord();
			grid = getfilteredGrid(monthlyAttends,true); 
			l.setText("Monthly Report");
		}
		else{
			monthlyAttends = monthlyAttendService.getMonthlyAttendByUserId(Long.valueOf(session.getCurrentUser().getId()));
			grid = getfilteredGrid(monthlyAttends,false); 
			l.setText("Your Monthly Attendance");
		}
		Label leaveNote = new Label("Your remaining paid leaves balance (till year end) is " + String.valueOf(session.getCurrentUser().getNbOfLeave()));
		leaveNote.getElement().getStyle().set("font-weight", "bold");
		leaveNote.getElement().getStyle().set("font-size", "100%");
		add(l,grid,leaveNote);
	}
	
	private Grid<MonthlyAttend> getGlobalReportGrid(List<MonthlyAttend> attend,Boolean showName) {
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
	
	private Grid<MonthlyAttend> getfilteredGrid(List<MonthlyAttend> attends,boolean showName){
		List<MonthlyAttend> attendList = attends;
		Grid<MonthlyAttend> grid = new Grid<>();
		ListDataProvider<MonthlyAttend> dataProvider = new ListDataProvider<>(
				attendList);
		grid.setDataProvider(dataProvider);

		HeaderRow filterRow = grid.appendHeaderRow();
		
		if (showName) {
			Grid.Column<MonthlyAttend> firstNameColumn = grid.addColumn(MonthlyAttend::getFirstName).setHeader("Name");
			Grid.Column<MonthlyAttend> lastNameColumn = grid.addColumn(MonthlyAttend::getLastName)
					.setHeader("Last Name");
			// First filter
			TextField firstNameField = new TextField();
			firstNameField.addValueChangeListener(event -> dataProvider.addFilter(
					person -> StringUtils.containsIgnoreCase(person.getFirstName(), firstNameField.getValue())));

			firstNameField.setValueChangeMode(ValueChangeMode.EAGER);

			filterRow.getCell(firstNameColumn).setComponent(firstNameField);
			firstNameField.setSizeFull();
			firstNameField.setPlaceholder("First Name");

			// Second filter
			TextField lastNameField = new TextField();
			lastNameField.addValueChangeListener(event -> dataProvider.addFilter(attend -> StringUtils
					.containsIgnoreCase(String.valueOf(attend.getLastName()), lastNameField.getValue())));

			lastNameField.setValueChangeMode(ValueChangeMode.EAGER);

			filterRow.getCell(lastNameColumn).setComponent(lastNameField);
			lastNameField.setSizeFull();
			lastNameField.setPlaceholder("Last Name");

		}
		grid.addColumn(MonthlyAttend::getStartTime).setHeader("Start Time");
		grid.addColumn(MonthlyAttend::getEndTime).setHeader("End Time");

		grid.addColumn(MonthlyAttend::getNbOfHour).setHeader("Number of Hours");
		Grid.Column<MonthlyAttend> dateColumn = grid
		        .addColumn(MonthlyAttend::getDate)
		        .setHeader("Date");
		
		// thrid filter
		DatePicker dateField = new DatePicker();
		dateField.addValueChangeListener(event -> dataProvider.addFilter(attend -> StringUtils
				.containsIgnoreCase(String.valueOf(attend.getDate().toString()), dateField.getValue() != null ? dateField.getValue().toString(): "")));

		filterRow.getCell(dateColumn).setComponent(dateField);
		dateField.setSizeFull();
		dateField.setPlaceholder("Date");

		return grid;
	}
}

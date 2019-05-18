package com.kassem.finalproject.ui.view.user;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.User;
import com.kassem.finalproject.model.User.RoleValues;
import com.kassem.finalproject.service.UserService;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.data.value.HasValueChangeMode;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "book", layout = MenuBarView.class)
public class UserView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	UserService userService;

	@PostConstruct
	private void initUi() {
		setWidth("100%");
		// TODO use e.g. https://vaadin.com/directory/component/crud-ui-add-on
		Button button = new Button("Add User", e -> Notification.show(userService.add()));

		Grid<User> grid = getUserGrid();
		HorizontalLayout hLayout = new HorizontalLayout();
		hLayout.setSizeFull();
		hLayout.setAlignSelf(Alignment.CENTER);
		hLayout.add(grid);
		hLayout.add(button);
		add(hLayout);
	}

	private Grid<User> getUserGrid(/*List<User> users*/) {
		Grid<User> grid = new Grid<>();
	grid.setSelectionMode(SelectionMode.SINGLE);
	SingleSelect<Grid<User>, User> personSelect = grid.asSingleSelect();
	// personSelect can now be used with Binder or HasValue interface
	personSelect.addValueChangeListener(e -> {
		User selectedPerson = e.getValue();
		Notification.show(selectedPerson.getUsername());

	});
	ListDataProvider<User> dataProvider = new ListDataProvider<>(
			userService.getAllUser());
	grid.setDataProvider(dataProvider);

	Grid.Column<User> userNameColumn = grid
	        .addColumn(User::getUsername).setHeader("Name");
	Grid.Column<User> emailColumn = grid.addColumn(User::getEmail)
	        .setHeader("Email");
	Grid.Column<User> roleColumn = grid
	        .addColumn(user -> user.getRole())
	        .setHeader("Role");
	
	HeaderRow filterRow = grid.appendHeaderRow();
	//Filter By UserName;
	TextField userNameField = new TextField();
	userNameField.addValueChangeListener(event -> dataProvider.addFilter(
	        user -> StringUtils.containsIgnoreCase(user.getUsername(),
	                userNameField.getValue())));

	userNameField.setValueChangeMode(ValueChangeMode.EAGER);
	
	filterRow.getCell(userNameColumn).setComponent(userNameField);
	userNameField.setSizeFull();
	userNameField.setPlaceholder("Filter");
	
	//Filter By Email;
	
	TextField EmailField = new TextField();
	EmailField.addValueChangeListener(event -> dataProvider.addFilter(
	        user -> StringUtils.containsIgnoreCase(user.getEmail(),
	        		EmailField.getValue())));

	EmailField.setValueChangeMode(ValueChangeMode.EAGER);
	
	filterRow.getCell(emailColumn).setComponent(EmailField);
	EmailField.setSizeFull();
	EmailField.setPlaceholder("Filter");
	
	//Filter by Role
	ComboBox<RoleValues> roleField = new ComboBox<>();
	roleField.setItems(RoleValues.values());
	roleField.addValueChangeListener(event -> dataProvider.addFilter(
	        user -> StringUtils.containsIgnoreCase(user.getRole(),
	        		roleField.getValue().toString())));

	//roleField.setv.setValueChangeMode(ValueChangeMode.EAGER);
	
	filterRow.getCell(roleColumn).setComponent(roleField);
	roleField.setSizeFull();
	roleField.setPlaceholder("Filter");
	return grid;
	
	}
}
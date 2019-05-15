package com.kassem.finalproject.ui.view.user;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.User;
import com.kassem.finalproject.service.UserService;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
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
		Button button = new Button("Add book", e -> Notification.show(userService.add()));

		Grid<User> grid = getUserGrid(userService.getAllUser());
		grid.setSelectionMode(SelectionMode.SINGLE);
		SingleSelect<Grid<User>, User> personSelect = grid.asSingleSelect();
		// personSelect can now be used with Binder or HasValue interface
		personSelect.addValueChangeListener(e -> {
			User selectedPerson = e.getValue();
			Notification.show(selectedPerson.getUsername());

		});

		add(grid);
		add(button);
	}

	private Grid<User> getUserGrid(List<User> users) {
		Grid<User> grid = new Grid<>();
		grid.setItems(users);
		grid.addColumn(User::getUsername).setHeader("UserName");
		grid.addColumn(User::getEmail).setHeader("Email");
		grid.addColumn(User::getRole).setHeader("Role");

		return grid;
	}
}
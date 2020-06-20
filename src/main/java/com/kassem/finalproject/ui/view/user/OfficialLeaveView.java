package com.kassem.finalproject.ui.view.user;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.OfficialLeave;
import com.kassem.finalproject.service.OfficialLeaveService;
import com.kassem.finalproject.ui.login.SessionInfo;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "officialleave", layout = MenuBarView.class)
@PageTitle("Official Leave")
public class OfficialLeaveView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OfficialLeaveService officialLeaveService;
	private SessionInfo sessionInfo;
	private OfficialLeave selectedLeave;
	
	@PostConstruct
	public void init() {
		setAlignItems(Alignment.CENTER);
		Label label = new Label("Official Leaves");
		 label.getElement().getStyle().set("font-weight", "bold");
		 label.getElement().getStyle().set("font-size", "150%");
		sessionInfo = new SessionInfo();
		//add(new Label("here is Official View"));
		
		List<OfficialLeave> leaveList = officialLeaveService.getAllOfficialLeave();
		Grid<OfficialLeave> grid = new Grid<>();
		grid.setItems(leaveList);
		grid.addColumn(OfficialLeave::getStartDate).setHeader("Date");
		grid.addColumn(OfficialLeave::getOccasion).setHeader("Occasion");
		grid.addColumn(OfficialLeave::getNbOfDays).setHeader("Number Of Days");
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
				GridVariant.LUMO_ROW_STRIPES);
		grid.setSelectionMode(SelectionMode.SINGLE);

		add(label);
		add(grid);
		SingleSelect<Grid<OfficialLeave>, OfficialLeave> leaveSelected = grid.asSingleSelect();
		// personSelect can now be used with Binder or HasValue interface
		leaveSelected.addValueChangeListener(e -> {
			selectedLeave = e.getValue();
		});
		/*Button back = new Button("Back");
		back.setVisible(false);
		back.addClickListener(e ->getFormAfterSelection(offer));*/
		grid.addItemClickListener(
		        event -> getApplicantsByOfferDialog(selectedLeave,sessionInfo).open());
		if(sessionInfo.getCurrentUser().getRole().equalsIgnoreCase("Admin")) {
			Button addBtn = new Button("Add new", event -> {
				getApplicantsByOfferDialog(null,sessionInfo).open();
				});
			
			add(addBtn);
		}
		
	}
	public Dialog getApplicantsByOfferDialog(OfficialLeave leave,SessionInfo session) {
		
			TextField occasionField = new TextField("Occasion");
			occasionField.setValue(leave != null ? leave.getOccasion() : "");
			occasionField.setEnabled(session.getCurrentUser().getRole().equalsIgnoreCase("admin"));
			DatePicker startDateField = new DatePicker("Start Date");
			startDateField.setValue(leave != null ? leave.getStartDate() : LocalDate.now());

			TextField nbOfDaysField = new TextField("Number of Days");
			nbOfDaysField.setValue(leave != null ? leave.getNbOfDays().toString(): "");
			nbOfDaysField.setEnabled(session.getCurrentUser().getRole().equalsIgnoreCase("admin"));
			
			TextArea descriptionField = new TextArea("Description");
			descriptionField.setValue(leave!= null ? leave.getDescription() : "");
			descriptionField.setEnabled(session.getCurrentUser().getRole().equalsIgnoreCase("admin"));
			
			FormLayout formLayout = new FormLayout(occasionField,startDateField,nbOfDaysField,descriptionField);
			
			
			Dialog dialog = new Dialog();
			Button saveBtn = new Button("Update ", event -> {
				save(dialog,leave != null ? leave.getId() : null,occasionField,startDateField,nbOfDaysField,descriptionField);
				});
			
			Button deleteBtn = new Button("Delete", event -> {
				officialLeaveService.delete(leave);
				dialog.close();
				});
			HorizontalLayout btns = new HorizontalLayout(saveBtn,deleteBtn);
			Label label = new Label(leave.getOccasion());
			 label.getElement().getStyle().set("font-weight", "bold");
			 label.getElement().getStyle().set("font-size", "150%");
			VerticalLayout l = new VerticalLayout(label,formLayout);
			l.setAlignItems(Alignment.CENTER);
			if(session.getCurrentUser().getRole().equalsIgnoreCase("Admin"))
				l.add(btns);
			l.setSizeFull();
			dialog.add(l);
			
			//dialog.add(horizontalLayout);
			//dialog.setCloseOnEsc(false);
			//dialog.setCloseOnOutsideClick(false);
			
			return dialog;
	     }
	
	private void save(Dialog dialog,Long id, TextField occasion,DatePicker date, TextField nbofDays,TextArea description) {
		OfficialLeave leave = null;
		if(id == null) {
			leave  = new OfficialLeave();
			leave.setId(Long.valueOf( new Random().nextInt(1000000 + 1)));
		}
		else
			leave = officialLeaveService.getLeaveById(id);
		leave.setDescription(description.getValue());
		leave.setNbOfDays(Integer.valueOf(nbofDays.getValue()));
		leave.setOccasion(occasion.getValue());
		leave.setStartDate(date.getValue());
		officialLeaveService.save(leave);
		dialog.close();
	}
	
}

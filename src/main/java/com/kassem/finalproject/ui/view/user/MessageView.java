package com.kassem.finalproject.ui.view.user;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.Message;
import com.kassem.finalproject.service.MessageService;
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

@Route(value = "Messages", layout = MenuBarView.class)
@PageTitle("Messages")
public class MessageView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MessageService  messageService;
	@Autowired
	private SessionInfo session;
	@PostConstruct
	public void initUi() {
		setAlignItems(Alignment.CENTER);
		Label label = new Label("Messages");
		label.getElement().getStyle().set("font-weight", "bold");
		label.getElement().getStyle().set("font-size", "150%");
		session = new SessionInfo();
		List<Message> messages = messageService.getAllMessageByUserId(String.valueOf(session.getCurrentUser().getId()));
		add(label,getGrid(messages));
		
	}
	public Dialog getFormAfterSelection(Message message) {
    	HorizontalLayout horizontalLayout = new HorizontalLayout();
    	Label title = new Label("Title: " +message.getTitle());
    	title.getElement().getStyle().set("font-weight", "bold");
    	title.getElement().getStyle().set("font-size", "150%");
    	
    	Label from = new Label("From :" + message.getFromUserName());
    	from.getElement().getStyle().set("font-weight", "bold");
    	from.getElement().getStyle().set("font-size", "80%");
    	
    	Label context = new Label(message.getContext());
    	context.getElement().getStyle().set("font-size", "100%");
    	
    	Label date = new Label(message.getSentDate().toString());
    	date.getElement().getStyle().set("font-size", "100%");
    	
		Dialog dialog = new Dialog();
		Button deleteButton = new Button("Delete", event -> {
			messageService.deleteMessage(message);
		    dialog.close();
		});
		Button cancelButton = new Button("Cancel", event -> {
		    dialog.close();
		});
		VerticalLayout layout = new VerticalLayout(title,context,from,date);
		horizontalLayout.add(deleteButton,cancelButton);
		horizontalLayout.setAlignItems(Alignment.CENTER);
		FormLayout form = new FormLayout(layout,horizontalLayout );
		dialog.add(form);
		
		//dialog.add(horizontalLayout);
		dialog.setCloseOnEsc(true);
		dialog.setCloseOnOutsideClick(true);
		
		return dialog;
     }
     private Grid<Message> getGrid(List<Message> messages){
    	 Grid<Message> grid = new Grid<>();
 		grid.setItems(messages);
 		grid.addColumn(Message::getFromUserName).setHeader("From");
 		grid.addColumn(Message::getSentDate).setHeader("Date And Time");
 		grid.addColumn(Message::getTitle).setHeader("Title");
 		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
 				GridVariant.LUMO_ROW_STRIPES);
 		grid.setSelectionMode(SelectionMode.SINGLE);

 		SingleSelect<Grid<Message>, Message> messageSelect = grid.asSingleSelect();
 		// personSelect can now be used with Binder or HasValue interface
 		Message message = new Message();
 		messageSelect.addValueChangeListener(e -> {
 			Message selectedMessage = e.getValue();
 			selectedMessage.copy(selectedMessage,message);
 		});
 		grid.addItemClickListener(
 		        event -> getFormAfterSelection(message).open());
 		return grid;
     }
}

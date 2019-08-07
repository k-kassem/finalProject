package com.kassem.finalproject.ui.secure;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.DailyAttend;
import com.kassem.finalproject.service.DailyAttendService;
import com.kassem.finalproject.ui.joboffer.JobOfferView;
import com.kassem.finalproject.ui.login.SessionInfo;
import com.kassem.finalproject.ui.view.category.CategoryView;
import com.kassem.finalproject.ui.view.user.AttendanceView;
import com.kassem.finalproject.ui.view.user.UserView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

@ParentLayout(SecureComponent.class)
public class MenuBarView extends HorizontalLayout implements RouterLayout {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VerticalLayout menu;
	@Autowired
	private DailyAttendService attendService;

	private SessionInfo session;
    public MenuBarView() {
    	session = new SessionInfo();
        setSizeFull();
        menu = new VerticalLayout();
        menu.setSizeUndefined();
        menu.setSpacing(false);
        add(menu);

        if(session.getCurrentUser().getRole().equalsIgnoreCase("Admin")){
        	addMenuElement(UserView.class, "Users", VaadinIcon.USER);
        	addMenuElement(JobOfferView.class, "New Offer", VaadinIcon.NEWSPAPER);
        	addMenuElement(AttendanceView.class, "Show Attendance", VaadinIcon.ARCHIVE);
        }
       // addMenuElement(SecureView.class, "Secure", VaadinIcon.LOCK);
        //addMenuElement(CategoryView.class, "Categories", VaadinIcon.CAR);
    }

    private void addMenuElement(Class<? extends Component> navigationTarget,
                                String name, VaadinIcon icon) {
        Button button = new Button(name, new Icon(icon));
        button.setSizeFull();
        button.setHeight("75");
        button.addClickListener(e -> {
            button.getUI().ifPresent(ui -> ui.navigate(navigationTarget));
        });
        menu.add(button);
    }

    protected void onAttach(AttachEvent attachEvent) {
        UI ui = getUI().get();
        
        Button button = new Button("Logout", event -> {
        	doProcessAfterLogout(String.valueOf(session.getCurrentUser().getId()));
            // Redirect this page immediately
            ui.getPage().executeJavaScript(
                    "window.location.href='/logout'");

            // Close the session
            ui.getSession().close();
        });

        button.setIcon(new Icon(VaadinIcon.EXIT));
        button.setSizeFull();
        button.setHeight("75");
        menu.add(button);

        // Notice quickly if other UIs are closed
        ui.setPollInterval(3000);
    }

    public void doProcessAfterLogout(String id) {
    	DailyAttend attend  = attendService.getbyId(id);
    	attendService.removeRecord(attend);
    }
}
package com.kassem.finalproject.ui.secure;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.DailyAttend;
import com.kassem.finalproject.model.MonthlyAttend;
import com.kassem.finalproject.service.DailyAttendService;
import com.kassem.finalproject.service.MonthlyAttendService;
import com.kassem.finalproject.ui.joboffer.JobOfferView;
import com.kassem.finalproject.ui.login.SessionInfo;
import com.kassem.finalproject.ui.view.user.AttendanceView;
import com.kassem.finalproject.ui.view.user.LeaveRequestView;
import com.kassem.finalproject.ui.view.user.MonthlyReportView;
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
	@Autowired
	private MonthlyAttendService monthlyAttendService;
	@Autowired
    private DailyAttendService dailyAttendService;

	private SessionInfo session;
    public MenuBarView() {
    	session = new SessionInfo();
        setSizeFull();
        menu = new VerticalLayout();
        menu.setSizeUndefined();
        menu.setSpacing(false);
        add(menu);

        addMenuElement(SecureView.class, "Profile", VaadinIcon.HOME);
        if(session.getCurrentUser().getRole().equalsIgnoreCase("Admin")){
        	addMenuElement(UserView.class, "Users", VaadinIcon.USER);
        	addMenuElement(JobOfferView.class, "New Offer", VaadinIcon.NEWSPAPER);
        	addMenuElement(AttendanceView.class, "Show Attendance", VaadinIcon.ARCHIVE);
        }
        addMenuElement(LeaveRequestView.class, "Leave Permission", VaadinIcon.OUT);
        if(session.getCurrentUser().getRole().equalsIgnoreCase("Admin")){
        //add All Leave leave permission 
        
        }
        addMenuElement(MonthlyReportView.class, "Monthly Report", VaadinIcon.RECORDS);
        buildCheckInOutBtn();
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
        	//doProcessAfterLogout(String.valueOf(session.getCurrentUser().getId()));
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

    private void doProcessAfterLogout(String id) {
    	DailyAttend attend  = attendService.getbyId(id);
    	MonthlyAttend monthlyAttend = createMonthlyRecord(attend);
    	monthlyAttendService.saveRecord(monthlyAttend);
    	attendService.removeRecord(attend);
    }
    
    private MonthlyAttend createMonthlyRecord(DailyAttend attend){
    	MonthlyAttend monthlyAttend = new MonthlyAttend();
    	monthlyAttend.setId( new Random().nextInt(1000000 + 1));
    	
    	String firstName = session.getCurrentUser().getFirstName();
    	String lastName = session.getCurrentUser().getLastName();
    	String startTime = attend.getStartTime();
    	String endTime = String.valueOf(LocalTime.now().getHour()) + ":" + String.valueOf(LocalTime.now().getMinute());
    	double nbOfHours = 0;
    	String[] startTimeAr = startTime.split(":");
    	String[] endTimeAr = startTime.split(":");
    	double hours = Double.valueOf(endTimeAr[0]) - Double.valueOf(startTimeAr[0]);
    	double minute = Double.valueOf(endTimeAr[1]) - Double.valueOf(startTimeAr[1]);
    	nbOfHours = hours + (minute/60);
    	LocalDate date = LocalDate.now();
    	monthlyAttend.setDate(date);
    	monthlyAttend.setEndTime(endTime);
    	monthlyAttend.setFirstName(firstName);
    	monthlyAttend.setLastName(lastName);
    	monthlyAttend.setNbOfHour(nbOfHours);
    	monthlyAttend.setStartTime(startTime);
    	monthlyAttend.setUserId(String.valueOf(session.getCurrentUser().getId()));
    	return monthlyAttend;
    }
    private void buildCheckInOutBtn(){
    	 Button checkIn = new Button("Check In ", new Icon(VaadinIcon.INPUT));
    	 Button checkOut = new Button("Check Out ", new Icon(VaadinIcon.OUT));
    	 checkOut.setEnabled(false);
    	 checkIn.setSizeFull();
    	 checkIn.setHeight("75");
    	 checkIn.addClickListener(e -> {
        	 DailyAttend dailyAttend = createDailyAttend();
 			 dailyAttendService.saveDailyAttend(dailyAttend);
 			checkIn.setEnabled(false);
 			getButton(checkOut);
         });
         
        
         checkOut.setSizeFull();
         checkOut.setHeight("75");
         checkOut.addClickListener(e -> {
        	 doProcessAfterLogout(String.valueOf(session.getCurrentUser().getId()));
        	 checkIn.setEnabled(true);
        	 checkOut.setEnabled(false);
        	 
         });
         menu.add(checkIn);
         menu.add(checkOut);
    }
    
    private DailyAttend createDailyAttend() {
    	SessionInfo session = new SessionInfo();
    	DailyAttend dailyAttend = new DailyAttend();
    	String  userId = String.valueOf(session.getCurrentUser().getId()) ;
    	String userName = session.getCurrentUser().getUsername();
    	String startTime = String.valueOf(LocalTime.now().getHour()) + ":" + String.valueOf(LocalTime.now().getMinute());
    	LocalDate date = LocalDate.now();
    	long id = new Random().nextInt((99 - 1) + 1) + 15;
    	dailyAttend.setId(id);
    	dailyAttend.setUserId(userId);
    	dailyAttend.setStartTime(startTime);
    	dailyAttend.setDate(date);
    	dailyAttend.setUserName((userName));
    	return dailyAttend;
    }
    private void getButton(Button btn){
    	btn.setEnabled(true);
    	
    }
}
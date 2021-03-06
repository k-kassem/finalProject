package com.kassem.finalproject.ui.login;

import ch.qos.logback.classic.Logger;

import com.kassem.finalproject.model.DailyAttend;
import com.kassem.finalproject.model.User;
import com.kassem.finalproject.service.DailyAttendService;
import com.kassem.finalproject.service.UserService;
import com.kassem.finalproject.utils.AppUtils;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("login")
public class LoginComponent extends VerticalLayout implements HasUrlParameter<String> {
    private Logger logger = (Logger) LoggerFactory.getLogger(LoginComponent.class);

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button forgotPasswordBtn;

    private Label logoutLabel;

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserService userService;
    @Autowired
    private DailyAttendService dailyAttendService;

    public LoginComponent() {
        setAlignItems(Alignment.CENTER);

        Image img = new Image("images/test3.jpg","test");
        img.setWidth("350px");
        img.setHeight("200px");
        add(img);
        logoutLabel = new Label();
        logoutLabel.getElement().getStyle().set("color", "red");
        logoutLabel.setVisible(false);
        add(logoutLabel);

        usernameField = new TextField();
        usernameField.setLabel("Username");

        passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.addKeyPressListener(Key.ENTER, listener -> {
            logger.debug("Enter pressed");
            loginAction();
        });

        loginButton = new Button("Login");
        loginButton.setSizeFull();
        loginButton.addClickListener(e -> {
            logger.debug("Login button pressed");
            loginAction();
        });
        forgotPasswordBtn = new Button("Forgot Password");
        forgotPasswordBtn.setSizeFull();
        forgotPasswordBtn.addClickListener(e -> {
            logger.debug("Forget Password button pressed");
            forgotPasswordBtn.getUI().ifPresent(ui -> ui.navigate("forgotpassword"));
        });

       // HorizontalLayout loginAndForgetHLayout = new HorizontalLayout(loginButton,forgotPasswordBtn);
        VerticalLayout layout = new VerticalLayout(usernameField, passwordField,loginButton,forgotPasswordBtn );
        Image img1 = new Image("images/test2.jpg","test");
        img1.setWidth("700px");
        img1.setHeight("350px");
        HorizontalLayout hlayout = new HorizontalLayout(/*img1,*/layout);
        hlayout.setBoxSizing(BoxSizing.BORDER_BOX);
        add(hlayout);
    }

    private void loginAction() {
        String username = usernameField.getValue();
        String password = passwordField.getValue();
        loginButton.setEnabled(false);
		if (login(username, password) ) {
			/*DailyAttend dailyAttend = createDailyAttend();
			dailyAttendService.saveDailyAttend(dailyAttend);*/
			if(userService.getUserByUsername(username).getFirstLogin() == 0 || userService.getUserByUsername(username).getFirstLogin() == null)
				loginButton.getUI().ifPresent(ui -> ui.navigate("secure"));
			else {
				loginButton.getUI().ifPresent(ui -> ui.navigate("changepassword"));
			}
		} else {
            logoutLabel.setVisible(true);
            loginButton.setEnabled(true);
        }
    }

    private boolean login(String username, String password) {
        try {
            Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if (authenticate.isAuthenticated()) {
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authenticate);
                return true;
            }
        } catch (BadCredentialsException ex) {
            logoutLabel.setText("Incorrect username or password.");
            usernameField.focus();
            passwordField.setValue("");
        } catch (Exception ex) {
            Notification.show("An unexpected error occurred, please try again in a few minutes", 3000, Position.MIDDLE);
            logger.error("Unexpected error while logging in", ex);
        }
        return false;
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter != null && parameter.contentEquals("loggedout")) {
            logoutLabel.setVisible(true);
            logoutLabel.setText("You have been successfully logged out");
        }
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
    
}
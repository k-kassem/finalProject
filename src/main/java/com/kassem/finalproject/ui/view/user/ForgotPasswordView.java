package com.kassem.finalproject.ui.view.user;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.model.ConfirmationCode;
import com.kassem.finalproject.model.User;
import com.kassem.finalproject.service.ConfirmationCodeService;
import com.kassem.finalproject.service.UserService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

@Route("forgotpassword")
public class ForgotPasswordView extends VerticalLayout implements RouterLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	UserService userService;

	@Autowired
	ConfirmationCodeService confirmationCodeService;

	private TextField userIdFld;
	private TextField confirmationcodeFld;
	private PasswordField newPasswordFld;
	private PasswordField confirmPasswordFld;
	private Button submit;
	private ConfirmationCode conCode;

	private User user;
	
	@PostConstruct
	public void init() {
		setAlignItems(Alignment.CENTER);
		this.add(new Label("forgot your password"));
		conCode = new ConfirmationCode();

		confirmationcodeFld = new TextField();
		confirmationcodeFld.setLabel("Confirmation Code");
		confirmationcodeFld.setVisible(false);

		newPasswordFld = new PasswordField();
		newPasswordFld.setLabel("New Password");
		newPasswordFld.setVisible(false);

		confirmPasswordFld = new PasswordField();
		confirmPasswordFld.setLabel("Confirm Password");
		confirmPasswordFld.setVisible(false);

		userIdFld = new TextField();
		userIdFld.setLabel("User ID");

		submit = new Button("Submit");
		submit.setEnabled(false);
		
		Binder<ConfirmationCode> binder = new Binder<ConfirmationCode>(ConfirmationCode.class);
		
		Binder<User> userBinder = new Binder<User>(User.class);

		this.add(userIdFld, confirmationcodeFld, newPasswordFld, confirmPasswordFld,submit);

		userIdFld.addKeyPressListener(Key.ENTER, e -> {
			if (userIdFld.getValue().equals("") || userIdFld.getValue() == null) {
				Notification.show("please enter your user name");
				confirmationcodeFld.setVisible(false);
			} else {

				user = getUser(userIdFld.getValue());

				if (user != null) {
					Notification.show("confirmation code will be sent to your email :" + user.getEmail());
					confirmationcodeFld.setVisible(true);
					// Long code = Long.valueOf(new Random().nextInt(100000 + 1));
					// Notification.show(code.toString());
					saveConfirmationCode(userIdFld.getValue());
					conCode = confirmationCodeService.getCodeByUserName(userIdFld.getValue());
					

					// send code by email
					try {
						sendEmail("confirmation code",conCode.getCode().toString());
					} catch (AddressException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Notification.show("The User Username is not Valid");
					confirmationcodeFld.setVisible(false);
				}
			}

		});
		binder.forField(confirmationcodeFld)
				.withValidator(oldPasword -> isSameCode(confirmationcodeFld.getValue(), conCode),
						"Code are not the same, please enter the code sent to your email")
				.bind(ConfirmationCode::getCodeAsString, ConfirmationCode::setCodeAsString);

		userBinder.forField(confirmPasswordFld)
				.withValidator(confirmPassword -> isNewPasswordAreSame(newPasswordFld.getValue(),
						confirmPasswordFld.getValue()), "The new Password is not Valid")
				.bind(User::getPassword, User::setPassword);

		
		submit.addClickListener(event -> {
        	String newPassword = new String();
        	
        	newPassword = newPasswordFld.getValue();
        	
        	changePassword(newPassword,user);
        	Notification.show("Your password is changed, please login with the new Password");
        	submit.getUI().ifPresent(ui -> ui.navigate("login"));
        	
        });
	}

	private User getUser(String userName) {

		return userService.getUserByUsername(userName);
	}

	private void sendEmail(String Title, String message) throws AddressException, MessagingException, IOException {
		createCode();
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("kassemfortesting@gmail.com", "Kha@2020");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("kassemfortesting@gmail.com", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kkassem1432@gmail.com"));
		msg.setSubject(Title);
		msg.setContent(message, "text/html");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		msg.setContent(multipart);
		Transport.send(msg);
	}

	private String createCode() {
		return "";
	}

	private void saveConfirmationCode(String userId) {
		ConfirmationCode confirmationCode = confirmationCodeService.getCodeByUserName(userId);

		Long confirmationCodeId = Long.valueOf(new Random().nextInt(1000000 + 1));
		Long code = Long.valueOf(new Random().nextInt(100000 + 1));
		if (confirmationCode == null) {
			confirmationCode = new ConfirmationCode();
			confirmationCode.setId(confirmationCodeId);
			confirmationCode.setCode(code);
			confirmationCode.setUserId(userId);
		} else
			confirmationCode.setCode(code);
		confirmationCodeService.saveConfirmationCode(confirmationCode);
	}

	private boolean isSameCode(String code, ConfirmationCode conCode) {
		if (conCode == null || conCode.getCode() == null)
			return false;
		if (code.equals(conCode.getCode().toString())) {
			newPasswordFld.setVisible(true);
			confirmPasswordFld.setVisible(true);
			return true;

		}
		else {
			newPasswordFld.setVisible(false);
			confirmPasswordFld.setVisible(false);
		}
		// Notification.show("Code are not the same, please enter the code sent to your
		// email");
		return false;
	}
	private Boolean isNewPasswordAreSame(String newPwd,String confirmPwd) {
		if(newPwd.equals(confirmPwd)) {
			submit.setEnabled(true);
			return true;
		}
		else
			submit.setEnabled(false);
		return false;
	}

	private void changePassword(String oldPwd,User user) {
		user.setPassword(oldPwd);
		user.setFirstLogin(0);
		userService.UpdatePassword(user);
		submit.setVisible(false);
	}
}

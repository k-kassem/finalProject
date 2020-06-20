package com.kassem.finalproject.ui.view.user;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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

import com.kassem.finalproject.model.Applicant;
import com.kassem.finalproject.model.Applicant.ConnectionWay;
import com.kassem.finalproject.model.Applicant.Degrees;
import com.kassem.finalproject.model.JobOffer;
import com.kassem.finalproject.service.ApplicantService;
import com.kassem.finalproject.service.JobOfferService;
import com.kassem.finalproject.ui.login.SessionInfo;
import com.kassem.finalproject.ui.secure.MenuBarView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "applicants", layout = MenuBarView.class)
@PageTitle("Applicants")
public class ApplicantsView extends VerticalLayout{

	@Autowired
	private JobOfferService jobOfferService;
	@Autowired
	private ApplicantService applicantService;
	SessionInfo session;
	private Applicant selectedPerson = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void initUi() {
		//getStyle().set("background-color", "blue");
		 Label label = new Label("Applications");
		 label.getElement().getStyle().set("font-weight", "bold");
		 label.getElement().getStyle().set("font-size", "150%");
		setAlignItems(Alignment.CENTER);
		session = new SessionInfo();
		List<JobOffer> personList = jobOfferService.getAllOffer();
		Grid<JobOffer> grid = new Grid<>();
		grid.setItems(personList);
		grid.addColumn(JobOffer::getJobTitle).setHeader("Job Title");
		grid.addColumn(JobOffer::getSeniorityLevel).setHeader("Seniority Level");
		grid.addColumn(JobOffer::getEmployementType).setHeader("Employement Type");
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
				GridVariant.LUMO_ROW_STRIPES);
		grid.setSelectionMode(SelectionMode.SINGLE);

		add(label);
		add(grid);
		SingleSelect<Grid<JobOffer>, JobOffer> offerSelected = grid.asSingleSelect();
		// personSelect can now be used with Binder or HasValue interface
		JobOffer offer = new JobOffer();
		offerSelected.addValueChangeListener(e -> {
			JobOffer selectedPerson = e.getValue();
			selectedPerson.copy(selectedPerson,offer);
		});
		/*Button back = new Button("Back");
		back.setVisible(false);
		back.addClickListener(e ->getFormAfterSelection(offer));*/
		grid.addItemClickListener(
		        event -> getApplicantsByOfferDialog(offer).open());
		
		
		//add(back);
	}
	 public Dialog getApplicantsByOfferDialog(JobOffer offer) {
		 Label label = new Label("Applicants");
		 label.getElement().getStyle().set("font-weight", "bold");
		 label.getElement().getStyle().set("font-size", "150%");
		 List<Applicant> applicantList = applicantService.getApplicantByOfferId(offer.getId());
			Grid<Applicant> grid = new Grid<>();
			grid.setItems(applicantList);
			grid.addColumn(Applicant::getFirstName).setHeader("First Name");
			grid.addColumn(Applicant::getLastName).setHeader("Last Name");
			grid.addColumn(Applicant::getNationality).setHeader("Nationality");
			grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
					GridVariant.LUMO_ROW_STRIPES);
			grid.setSelectionMode(SelectionMode.SINGLE);

			SingleSelect<Grid<Applicant>, Applicant> applicantSelected = grid.asSingleSelect();
			// personSelect can now be used with Binder or HasValue interface
			Applicant applicant1 = new Applicant();
			applicantSelected.addValueChangeListener(e -> {
				selectedPerson = e.getValue();
				//selectedPerson.copy(selectedPerson,applicant1);
			});
			grid.addItemClickListener(
			        event -> getApplicantDialog(selectedPerson).open());
			Dialog dialog = new Dialog();
			VerticalLayout l = new VerticalLayout(label,grid);
			l.setSizeFull();
			l.setAlignItems(Alignment.CENTER);
			//dialog.add(label);
			dialog.add(l);
			dialog.setWidth("1000px");
			//dialog.add(horizontalLayout);
			//dialog.setCloseOnEsc(false);
			//dialog.setCloseOnOutsideClick(false);
			
			return dialog;
	     }
	 
	private Dialog getApplicantDialog(Applicant applicant) {
		Dialog dialog = new Dialog();
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		 TextField firstName = new TextField("First Name");
    	 firstName.setValue(applicant.getFirstName());
    	 
    	 TextField lastName = new TextField("Last Name");
    	 lastName.setValue(applicant.getLastName());
    	 
    	 TextField nationality = new TextField("Nationality");
    	 nationality.setValue(applicant.getNationality());
    	 
    	 TextField age = new TextField("Age");
    	 age.setValue(String.valueOf(applicant.getAge()));
    	 
    	 TextField email = new TextField("Email");
    	 email.setValue(applicant.getEmail());
    	 
    	 TextField phoneNumber = new TextField("Phone Number");
    	 phoneNumber.setValue(String.valueOf(applicant.getPhoneNumber()));
    	 FormLayout personalInfoLayout = new FormLayout(firstName,lastName,nationality,age,email,phoneNumber);
    	 
    	 //Fields related to education;
    	 TextField degree = new TextField("Degree");
    	 degree.setValue(applicant.getDegree().toString());
    	 
    	 TextField major = new TextField("Major");
    	 major.setValue(applicant.getMajor());
    	 
    	 TextField university = new TextField("University");
    	 university.setValue(applicant.getUniversity());
    	 
    	 DatePicker eduStartDate = new DatePicker("Start Date");
    	 eduStartDate.setValue(applicant.getEduStartDate());
    	 
    	 DatePicker eduEndDate = new DatePicker("End Date");
    	 eduEndDate.setValue(applicant.getEduEndDate());
    	 FormLayout educationLayout = new FormLayout(university,degree,major,eduStartDate,eduEndDate);
    	 
    	 //Field related to experience;
    	 TextField nbOfYear = new TextField("Number Of Year of Experience");
    	 nbOfYear.setValue(String.valueOf(applicant.getNbOfyear()));
    	 
    	 TextField company = new TextField("Compay");
    	 company.setValue(applicant.getCompany());
    	 
    	 TextField role = new TextField("Role");
    	 role.setValue(applicant.getRole());
    	 
    	 TextArea comment = new TextArea("Comment");
    	 comment.setValue(applicant.getComment());
    	 
    	 DatePicker expStartDate = new DatePicker("Start Date");
    	 expStartDate.setValue(applicant.getExpStartDate());
    	 
    	 DatePicker expEndDate = new DatePicker("End Date");
    	 expEndDate.setValue(applicant.getExpEndDate());
    	 FormLayout experienceLayout = new FormLayout(nbOfYear,company,role,comment,expStartDate,expEndDate);
    	 
    	 TextField connectionWayCbx = new TextField("Connecting Way");
    	 connectionWayCbx.setValue(applicant.getConnectingWay());
		Button sendButton = new Button("Approve", event -> {
			String title = "Update about you application";
			String message = "Hello "+firstName.getValue()+"\n" + 
					"\n" + 
					"Thank you for your interest to join Our company \n" + 
					"\n" + 
					"We have carefully reviewed your CV, and have decided  to proceed with your application. Our decision was based upon comparing your experience with the requirements of the position and the profile of the rest of the team. \n" + 
					"\n" + 
					"Kindly note that the interview is confirmed for Thursday August 29, at 1:00 PM" +"\n"+ 
					"\n" + 
					"Thank you again for your interest in Our Company.\n" + 
					"\n" +"\n"+ 
					"Kind regards,\n" + "\n"+
					session.getCurrentUser().getFirstName();;
			try {
				sendEmail(title,message);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dialog.close();
		});
		Button cancelButton = new Button("Reject", event -> {
			String title = "Update about you application";
			String message = "Hello "+firstName.getValue()+"\n" + 
					"\n" + 
					"Thank you for your interest to join Our company \n" + 
					"\n" + 
					"We have carefully reviewed your CV, and unfortunately have decided not to proceed with your application. Our decision was based upon comparing your experience with the requirements of the position and the profile of the rest of the team. \n" + 
					"\n" + 
					"our company is growing, we're constantly opening new roles. Please keep track of all new openings via our Careers Page \n" + 
					"\n" + 
					"Thank you again for your interest in Our Company.\n" + 
					"\n" +"\n"+ 
					"Kind regards,\n" + "\n"+
					session.getCurrentUser().getFirstName();
			try {
				sendEmail(title,message);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dialog.close();
		});
		horizontalLayout.add(sendButton, cancelButton);
		horizontalLayout.setAlignItems(Alignment.CENTER);
		FormLayout form = new FormLayout(personalInfoLayout,educationLayout,experienceLayout,connectionWayCbx,horizontalLayout);
		dialog.add(form);
		return dialog;
	}
	private void sendEmail(String Title ,String message) throws AddressException, MessagingException, IOException{
		Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("kkassem1432@gmail.com", "sa43m5890");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("kkassem1432@gmail.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("khalil_k97@hotmail.com"));
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
}

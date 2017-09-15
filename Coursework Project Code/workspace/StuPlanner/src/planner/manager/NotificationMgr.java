package planner.manager;

import java.util.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import planner.model.Student;
import planner.db.PlannerData;
import planner.model.Course;
import planner.model.CourseReg;

/** Manager to notify the users if there are any changes to their
 * 	registration of courses
 */
public class NotificationMgr {
	
	static ArrayList<CourseReg> registrationList = PlannerData.regList;
	static ArrayList<Course> courseList 		 = PlannerData.courseList;
	static ArrayList<Student> studentList 		 = PlannerData.studentList;
	
	/**
	 * Send both email and SMS for wait list notification
	 * @param studentToNotify
	 */
	public static void sendBoth (Student studentToNotify, String courseCode){
		sendEmail(studentToNotify, courseCode);
	    sendSMS(studentToNotify);
	}
	
	/**
	 * Send email for wait list notification
	 * @param studentToNotify
	 */
	public static void sendEmail(Student studentToNotify, String courseCode){
		String email = studentToNotify.getEmail();
		
		Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				//email and password of smtp server
				return new PasswordAuthentication("cz2002.fsp2.group5@gmail.com","cz2002@fsp2group5");
			}
		};

	    Session session = Session.getDefaultInstance(props, auth);
	    
	    try {
	        MimeMessage msg = new MimeMessage(session);
	        msg.setFrom("cz2002.fsp2.group5@gmail.com");
	        msg.setRecipients(Message.RecipientType.TO,email);
	        msg.setSubject("Waitlist Notification");
	        msg.setText("You have been registered to " + courseCode);
	        Transport.send(msg);
	    } catch (MessagingException mex) {
	        System.out.println("send failed, exception: " + mex);
	    }
	}
	
	/**
	 * Send SMS for wait list notification
	 * @param studentToNotify
	 */
	public static void sendSMS(Student studentToNotify)
	{
		int mobileNo = studentToNotify.getMobileNo();
		System.out.println("An SMS is sent to " + mobileNo);
	}
	
	/** Send alert when a user is registered to the course
	 * 	(initially in the waitlist)
	 * @param studentToNotify
	 * @param courseCode
	 * @param indexNumber
	 */
	public static void sendAlertWaitlist(Student studentToNotify, String courseCode, int indexNumber){
		if (studentToNotify.getNotiMode() == 1){
			// Sending Fake SMS
			sendSMS(studentToNotify);
		}
		else if (studentToNotify.getNotiMode() == 2){
			// Sending Actual Email
			sendEmail(studentToNotify, courseCode);
		}
		else if (studentToNotify.getNotiMode() == 3){
			sendBoth(studentToNotify, courseCode);
		}									
	
	}

}
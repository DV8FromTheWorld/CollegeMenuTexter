package net.dv8tion;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMail {
	public static void send(final String username, final String password, String recipient, String subject, String message) {
		Properties props = new Properties();
		//props.put("mail.smtp.host", "smtp.gmail.com");
		//props.put("mail.smtp.socketFactory.port", "465");
		//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		//props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.port", "465");
 
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username + "@gmail.com", password);
				}
			});
 
		try {
 
			Message email = new MimeMessage(session);
			email.setFrom(new InternetAddress("bob"));
			email.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipient));
			email.setSubject(subject);
			email.setText(message);
 
			Transport.send(email);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
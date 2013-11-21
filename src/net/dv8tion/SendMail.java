package net.dv8tion;

import java.util.Properties;
import java.util.logging.Level;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail
{
	public static String emailUsername = null;
	public static String emailPassword = null;

	/**
	 * Sends an email to the recipient specified. Uses the Email and password
	 * defined to send the email. Sends through Gmail, so the email must be a
	 * Gmail account.
	 * 
	 * @param recipient
	 *            The email that will receive the email.
	 * @param subject
	 *            The subject line of the email.
	 * @param message
	 *            The message of the email.
	 * @return Returns true if the message was sent. Does not confirm it being
	 *         received by the recipient.
	 */
	public static boolean send(String recipient, String subject, String message)
	{
		if (emailUsername == null || emailUsername.equals("username@gmail.com")
				|| emailPassword == null || emailPassword.isEmpty())
		{
			Core.logger.log(Level.SEVERE,
					"Email or password not set.  Cannot send email/text");
			return false;
		}
		Properties props = new Properties();

		// For SSL, Can't seem to get it to work though..
		// props.put("mail.smtp.host", "smtp.gmail.com");
		// props.put("mail.smtp.socketFactory.port", "465");
		// props.put("mail.smtp.socketFactory.class",
		// "javax.net.ssl.SSLSocketFactory");
		// props.put("mail.smtp.auth", "true");
		// props.put("mail.smtp.port", "465");

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator()
				{
					@Override
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(emailUsername,
								emailPassword);
					}
				});

		try
		{

			Message email = new MimeMessage(session);
			email.setFrom(new InternetAddress("bob"));
			email.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipient));
			email.setSubject(subject);
			email.setText(message);

			Transport.send(email);

			System.out.println("Done");
			return true;

		}
		catch (MessagingException e)
		{
			throw new RuntimeException(e);
		}
	}
}
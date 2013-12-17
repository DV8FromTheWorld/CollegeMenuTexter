package net.dv8tion;

import java.util.Properties;
import java.util.logging.Level;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
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
			Core.logger
					.log(Level.SEVERE,
							"Email or password not set.  Cannot send email/text\n"
									+ "Check Options.cfg and set Email and Password options.");
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

	public static void readGmail()
	{
		Properties props2 = System.getProperties();
		props2.setProperty("mail.store.protocol", "imaps");
		Session session2 = Session.getDefaultInstance(props2, null);

		try
		{
			Store store = session2.getStore("imaps");
			store.connect("imap.gmail.com", emailUsername, emailPassword);
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			Message messages[] = folder.getMessages();
			for (Message mes : messages)
			{
				Multipart multipart = (Multipart) mes.getContent();
				System.out.println(mes.getSubject());

				for (int i = 0; i < multipart.getCount(); i++)
				{
					BodyPart bodypart = multipart.getBodyPart(i);
					if (Part.ATTACHMENT.equalsIgnoreCase(bodypart
							.getDisposition()))
					{
						System.out.println("\tATTATCHMENT: "
								+ bodypart.toString());
					}
				}
			}
			folder.close(true);
			store.close();
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
/**
 * Core.java
 * 
 * @author Austin Keener (DV8FromTheWorld)
 * @version v1.0.0 Nov 18, 2013
 */
package net.dv8tion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Description Here (DONT FORGET YOU FOOL!)
 */
public class Core
{
	public static Logger logger;

	/**
	 * Location of the beginning of the program.
	 * Calls folder creation, logger init, and parsing.
	 * 
	 * @param args
	 *            Command Line Arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		createFolders();
		setupLogger();
		loadConfig();
		// send();
		receive();
	}

	private static void send()
	{
		Scanner key = new Scanner(System.in);
		AppstateMenuParser.parseTraditions();
		System.out.println("Enter number and @--.---");
		String phoneNumber = key.nextLine();
		System.out.println("Enter message:");
		String message = key.nextLine();
		SendMail.send(phoneNumber, "Test", message);
		key.close();
	}

	private static void receive()
	{

		SendMail.readGmail();
	}

	/**
	 * Initializes the logger. Logs are specified by Log <date>.txt.
	 * Logs are stored in the Logs folder.
	 */
	private static void setupLogger()
	{
		logger = Logger.getLogger("Menu-Logger");
		try
		{
			Date d = new Date();
			FileHandler handler = new FileHandler("Logs/Log "
					+ d.toString().replaceAll(":", "_") + ".txt");
			// handler.setFormatter(new Formatter());
			logger.addHandler(handler);
			logger.info("Logger succesfully created!");
			System.out.println(d);
		}
		catch (SecurityException e)
		{
			System.out.println("Error in adding a handler to the logger.");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.println("Could not find or create log file");
			e.printStackTrace();
		}
	}

	/**
	 * Creates the following folders: Logs, Menus, PhoneNumbers
	 * Places them in the root Dir.
	 */
	private static void createFolders()
	{
		String[] folders = { "Logs", "Menus", "PhoneNumbers" };
		for (int i = 0; i < folders.length; i++)
		{
			File file = new File(folders[i]);
			if (!file.exists())
			{
				if (!file.mkdir())
				{
					System.out.println("Could not create the " + folders[i]
							+ " folder.");
				}
			}
		}
	}

	/**
	 * Creates the config file and loads it.
	 * 
	 * @throws IOException
	 *             Throws if the Config.cfg file cannot be created.
	 */
	private static void loadConfig() throws IOException
	{
		BufferedReader config;
		File file = new File("Config.cfg");
		if (!file.exists())
		{
			file.createNewFile();
			PrintWriter pr = new PrintWriter(file);
			pr.print("#Email account to send texts/emails with.  Has to be a gmail account.\n"
					+ "#Needs to be in the format: username@gmail.com\n"
					+ "EMAIL: username@gmail.com\n"
					+ "#Used to login to email account.\n"
					+ "PASSWORD: password");
			pr.close();
		}
		config = new BufferedReader(new FileReader(file));
		String line;
		while ((line = config.readLine()) != null)
		{
			if (!line.contains("#") && !line.equals(""))
			{
				String option = line.substring(0, line.indexOf(':'));
				switch (option.trim().toUpperCase())
				{
					case "EMAIL":
						SendMail.emailUsername = line.substring(
								line.indexOf(':') + 1).trim();
						break;
					case "PASSWORD":
						SendMail.emailPassword = line.substring(
								line.indexOf(':') + 1).trim();
						break;
					default:
						logger.log(Level.SEVERE,
								"Could not identify config option.  Please check config for error.");
				}
			}
		}
		config.close();
	}
}

/**
 * Core.java
 * 
 * @author Austin Keener (DV8FromTheWorld)
 * @version v1.0.0 Nov 18, 2013
 */
package net.dv8tion;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Class Description Here (DONT FORGET YOU FOOL!)
 */
public class Core
{
	public static Logger logger;

	/**
	 * Location of the beginning of the program.
	 * calls folder creation, logger init, and parsing.
	 * 
	 * @param args Command Line Arguments
	 */
	public static void main(String[] args)
	{
		Scanner key = new Scanner(System.in);
		createFolders();
		setupLogger();

		// setupConfig();
		AppstateMenuParser.parseTraditions();
		System.out.println("Enter number and @--.---");
		String phoneNumber = key.nextLine();
		System.out.println("Enter message:");
		String message = key.nextLine();
		SendMail.send("appstatemenus", "appstatemenu", phoneNumber, "Test", message);
		key.close();
	}

	/**
	 * Initializes the logger.
	 * Logs are specified by Log <date>.txt
	 * Logs are stored in the Logs folder.
	 */
	private static void setupLogger()
	{
		logger = Logger.getLogger("Menu-Logger");
		try
		{
			Date d = new Date();
			logger.addHandler(new FileHandler("Logs/Log "
					+ d.toString().replaceAll(":", "_") + ".txt"));
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
	 * Creates the following folders:
	 * 		Logs, Menus, PhoneNumbers
	 * Places them in the root Dir.
	 */
	private static void createFolders()
	{
		String[] folders = {"Logs", "Menus", "PhoneNumbers"};
		for (int i = 0; i < folders.length; i++)
		{
			File file = new File(folders[i]);
			if (!file.exists())
			{
				if(!file.mkdir())
				{
					System.out.println("Could not create the " + folders[i] + " folder.");
				}
			}
		}
	}
	
}

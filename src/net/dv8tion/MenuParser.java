/**
 * MenuParser.java
 * 
 * @author Austin Keener (DV8FromTheWorld)
 * @version v1.0.0 Nov 18, 2013
 */
package net.dv8tion;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

/**
 * Base class for all menu parsers. Provides easy file writing and webpage
 * retrieval.
 */
public class MenuParser
{

	private static PrintWriter pr = null;
	
	/**
	 * Writes the menu to the file specified.
	 * 
	 * @param fileName The name of the file (usually the restaurant name + "menu.txt")
	 * @param menu The menu, formatted as a string. 
	 */
	protected static void writeMenuToFile(String fileName, String menu)
	{
		try
		{
			pr = new PrintWriter(new File("Menus/" + fileName));
			pr.println(menu);
			pr.close();
			Core.logger.info("Successfully printed and saved menu to:"
					+ fileName);
		}
		catch (IOException e)
		{
			Core.logger.log(Level.SEVERE, "Could not print menu to file: "
					+ fileName);
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves a webpage to be read through.
	 * 
	 * @param url The url of the requested webpage.
	 * @return Returns the webpage to read through as a BufferedReader
	 */
	protected static BufferedReader getWebPage(String url)
	{
		try
		{
			return new BufferedReader(new InputStreamReader(
					new URL(url).openStream()));
		}
		catch (MalformedURLException e)
		{
			Core.logger.log(Level.SEVERE, "Url provided was malformed. URL: "
					+ url);
			Core.logger.log(Level.SEVERE, e.getMessage());
		}
		catch (IOException e)
		{
			Core.logger.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}

	/**
	 * Retrieves the line that contains the key.
	 * 		Used to find the menu in the html code.
	 * 
	 * @param key The string that specifies which line to return. Should be unique to that line.
	 * @param in A webpage as a BufferedString.
	 * @return Returns a line from the webpage that contains the key.
	 */
	protected static String getLineFromKey(String key, BufferedReader in)
	{
		String line = null;
		try
		{
			while ((line = in.readLine()) != null)
			{
				int index = line.indexOf(key);
				if (index != -1)
				{
					return line;
				}
			}
		}
		catch (IOException e)
		{
			Core.logger.log(Level.SEVERE,
					"Issue when reading webpage for Traditions.");
			Core.logger.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
}

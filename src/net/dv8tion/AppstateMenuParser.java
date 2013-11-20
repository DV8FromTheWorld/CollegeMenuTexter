/**
 * AppstateMenuParser.java
 * 
 * @author Austin Keener (DV8FromTheWorld)
 * @version v1.0.0 Nov 18, 2013
 */
package net.dv8tion;

/**
 * Parses the menus of Appalachian State and writes the menus to file.
 */
public class AppstateMenuParser extends MenuParser
{
	/**
	 * Retrieves the webpage for the restaurant: "Traditions"
	 * Proceeds to then parse through the webcode and extract the menu.
	 * Writes the menu to file in the format: 
	 * 		<day> <date> | [url] <item> | [url] <item> |
	 */
	public static void parseTraditions()
	{
		int index = 0;
		String menu = "";
		String key = "Sanford Commons in the Central Dining Hall</p><p><strong>Traditions</strong>";
		String line = getLineFromKey(key, getWebPage("http://foodservices.appstate.edu/node/178"));
		if (line != null)
		{
			String[] days = {"Mon ", "Tues ", "Wed ", "Thurs ", "Fri ", "Sat ", "Sun "};
			for (int i = 0; i < 7; i++)
			{
				line = line.substring(line.indexOf(days[i]));
				index = line.indexOf('<');
				menu += line.substring(0, index) + " | ";
				line = line.substring(index);
				for (int j = 0; j < 2; j++)
				{
					line = line.substring(line.indexOf("</td><td align=\"left\">") + "</td><td align=\"left\">".length());
					index = line.indexOf("<a");
					if (index == 0)
					{
						line = line.substring(line.indexOf('"') + 1);
						index = line.indexOf('"');
						menu += "[URL]" + line.substring(0, index) + "[/URL] ";
						line = line.substring(line.indexOf('>') + 1);
						index = line.indexOf('<');
						menu += line.substring(0, index);
						line = line.substring(index);
					}
					else
					{
						index = line.indexOf('<');
						menu += line.substring(0, index);;
						line = line.substring(index);
					}
					menu += " | ";
				}
				menu += "\n";
			}

			menu = menu.replaceAll("&nbsp;", " ");
			menu = menu.replaceAll("&amp;", "&");	
			writeMenuToFile("TraditionsMenu.txt", menu);
		}
		else
		{
			System.out.println("null");
		}
	}
}

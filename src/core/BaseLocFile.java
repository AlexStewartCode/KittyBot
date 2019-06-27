package core;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import dataStructures.TaggedPairStore;
import utils.GlobalLog;
import utils.LogFilter;
import utils.io.FileMonitor;
import utils.io.FileUtils;


// A quick-and-dirty localization tool that scrapes the project for calls to itself, then
// generates/updates a file externally with all the stub values as keys that are localized.
public abstract class BaseLocFile
{
	// Filename
	public final String functionName; // Example: "Localizer.Stub";
	
	// Local translation storage
	protected TaggedPairStore stringStore; 
	
	// Logging
	private void log(String str) { GlobalLog.log(LogFilter.Strings, str); }
	private void warn(String str) { GlobalLog.warn(LogFilter.Strings, str); }
	private void error(String str) { GlobalLog.error(LogFilter.Strings, str); }
	
	// File monitoring
	protected FileMonitor fileMonitor;
	
	// Constructor 
	public BaseLocFile(String functionName)
	{
		this.functionName = functionName;
	}

	// Structure used for holding a pair of strings and any other info we need 
	// about localized information that is being looked up.
	private class LocInfo
	{
		public String file;
		public String phrase;
		
		public LocInfo(String f, String p)
		{
			this.file = f;
			this.phrase = p;
		}
	}
	
	// Do processing on each path in the scraped directory here, assuming it's .java
	public void stripForContents(Path path, ArrayList<LocInfo> strings)
	{
		String filename = path.getFileName().toString();
		if(filename.contains(".java"))
		{
			String contents = FileUtils.readContent(path);
			String[] split = contents.split(functionName);
			
			// Identify all localizer function calls
			for(int i = 1; i < split.length; ++i)
			{
				int loc = split[i].indexOf(")");
				String noWhitespace = split[i].replaceAll("\\s+","");
				
				if(loc != -1)
				{
					if(noWhitespace.charAt(noWhitespace.indexOf(")") - 1) == '"' && split[i].charAt(loc - 2) != '\\')
					{
						// At this point, we find the first ), then verify there's a ") behind it, and that
						// the " is not an escaped character.
						try
						{
							String toLocalize = split[i].substring(2, loc - 1);
							
							strings.add(new LocInfo(filename.substring(0, filename.lastIndexOf('.')), toLocalize));
							
							log("Found lookup call in " + path + ": " + toLocalize);
						}
						catch(IndexOutOfBoundsException e)
						{
							warn("Found phrase but couldn't parse in file " + path);
						}
					}
				}
			}
		}
	}
	
	// Nothing for now, but in the future will return a parsed and localized version of
	// the string in question if one can be found. If the localized string is empty, 
	// returns a the key instead which is the default phrase.
	public String getKey(String input)
	{
		if(stringStore == null)
			return input;
		
		String value = stringStore.getKey(input);
		if(value == null || value.trim().length() < 1)
			return input;
		
		return value;
	}

	// Update localization from the disk on file. Creates the file if it doesn't exist.
	// This file is internally formatted as an ini file.
	public void updateLocFromString(String fileContents)
	{
		log("Attempting to read localization file contents");
		
		stringStore = new TaggedPairStore(fileContents);
	}

	private void tryStripSpecified(Path path, ArrayList<LocInfo> toFill)
	{
		try
		{
			stripForContents(path, toFill);
		}
		catch(Exception e)
		{
			error("issue with file: " + path.toString());
		}
	}
	
	// Scrape the project and generate all the possible localizeable phrases.
	// This stubs out phrases to be localized.
	public void scrapeAll()
	{
		ArrayList<LocInfo> localizeList = new ArrayList<LocInfo>();
		FileUtils.acquireAllFiles(Constants.SourceDirectory).forEach((path) -> tryStripSpecified(path, localizeList));
				
		for(LocInfo toStub : localizeList)
			stringStore.addKeyValue(toStub.file, toStub.phrase, toStub.phrase);
	}
	
	// Converts this to a string
	public String toString()
	{
		return stringStore.toString();
	}
	
	public List<ConfigItem> toConfigList();
	{
		
	}
}

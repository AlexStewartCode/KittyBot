package core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Vector;
import utils.GlobalLog;
import utils.LogFilter;
import utils.io.FileMonitor;
import utils.io.FileUtils;

public class DEPRECATED_Config
{
	private static final String filepath = Constants.AssetDirectory + Constants.ConfigFilename; 
	
	public static DEPRECATED_Config instance;

	// Private
	private Vector<DEPRECATED_IConfigSection> sections;
	private FileMonitor monitoredConfigFile;
	
	public DEPRECATED_Config()
	{
		if(instance == null)
		{
			// Create local variables
			sections = new Vector<DEPRECATED_IConfigSection>();
			
			// Add all sections
			sections.add(new LocCommands());
			sections.add(new LocStrings());
			sections.add(new CommandEnabler());
			
			// Read file in and parse everything out
			performStartup();
			
			// Being monitoring configs and mark this as the instance now that it's made
			monitoredConfigFile = new FileMonitor(filepath);
			instance = this;
		}
		else
		{
			GlobalLog.error(LogFilter.Core, "You can't have two of the following: " + this.getClass().getSimpleName());
			System.exit(-1);
		}
	}
	
	// Perform startup
	private void performStartup()
	{
		File configFile = new File(filepath);
		
		if(configFile.exists())
		{
			reformConfig(configFile);
		}
		else
		{
			try
			{
				configFile.createNewFile();
			}
			catch (IOException e)
			{
				GlobalLog.error(LogFilter.Core, "Failed to load config or create empty config at " + filepath);
				System.exit(-1);
			}
		}
	}
	
	public static final String sectionStart = "[[";
	public static final String sectionEnd = "]]";
	public static final String headerStart = "[";
	public static final String headerEnd = "]";
	public static final String pairSplit = "=";
	public static final String pairSeparator = "\n";
	
	private void reformConfig(File configFile)
	{
		// Read and update
		String configContents = FileUtils.readContent(configFile);
		readConfigs(configContents);
		
		// Form updated data as necessary for autogeneration
		String output = combineConfigs();

		// Write to file.
		try
		{
			OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8);
			fileWriter.write(output);
			fileWriter.close();
		}
		catch (IOException e)
		{
			GlobalLog.error(LogFilter.Core, "Config writing failure.");
			GlobalLog.error(LogFilter.Core, e.getMessage());
		}
	}
	
	public void readConfigs(String fullContents)
	{
		String[] str = fullContents.split(pairSeparator);
		HashMap<String, String> parsedSections = new HashMap<String, String>();
		
		String currentHeader = "";
		for(int i = 0; i < str.length; ++i)
		{
			String line = str[i].trim();
			if(line.startsWith(sectionStart) && line.endsWith(sectionEnd))
			{
				currentHeader = line.substring(sectionStart.length(), line.length() - sectionEnd.length());
			}
			else
			{
				String sectionContents = parsedSections.getOrDefault(currentHeader, "");
				parsedSections.put(currentHeader, sectionContents + line + pairSeparator);
			}
		}
		
		for(int i = 0; i < sections.size(); ++i)
		{
			DEPRECATED_IConfigSection section = sections.get(i);
			String header = section.getHeader();
			String content = parsedSections.getOrDefault(header, null);
			
			if(content != null)
			{
				section.read(content);
			}
			else
			{
				GlobalLog.warn(LogFilter.Core, "Mismatch during config parsing - expected but did not find " + header);
			}
		}
	}
	
	public String combineConfigs()
	{
		String output = "";
		
		for(int i = 0; i < sections.size(); ++i)
		{
			// If not the first section, add spacing!
			if(i != 0)
			{
				for(int spacing = 0; spacing < 3; ++spacing)
				{
					output += pairSeparator;
				}
			}
			
			DEPRECATED_IConfigSection section = sections.get(i);
			output += sectionStart + section.getHeader() + sectionEnd + pairSeparator;
			output += section.write() + pairSeparator;
		}
		
		return output;
	}
	
	public void upkeep()
	{
		monitoredConfigFile.update((monitoredFile) -> {
			File configFile = new File(filepath);
			reformConfig(configFile);
		});
	}
	
}

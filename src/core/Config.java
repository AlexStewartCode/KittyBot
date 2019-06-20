package core;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import utils.GlobalLog;
import utils.LogFilter;
import utils.io.FileMonitor;
import utils.io.FileUtils;

public class Config
{
	private static final String filepath = Constants.AssetDirectory + Constants.ConfigFilename; 
	
	public static Config instance;
	
	// Accessable config related things
	public LocStrings locStrings;
	public LocCommands locCommands;
	public CommandEnabler commandEnabler;
	
	// Private
	private Vector<IConfigSection> sections;
	private FileMonitor monitoredConfigFile;
	
	public Config()
	{
		if(instance == null)
		{
			// Create local variables
			sections = new Vector<IConfigSection>();
			
			// Add all sections
			sections.add(new CommandEnabler());
			sections.add(new LocStrings());
			sections.add(new LocCommands());
			
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
	
	private void performStartup()
	{
		File configFile = new File(filepath);
		if(configFile.exists())
		{
			String configContents = FileUtils.readContent(new File(filepath));
			reformConfig(configContents);
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
	
	private void reformConfig(String fullContents)
	{
		for(IConfigSection section : sections)
		{
			
		}
	}
	
	public void upkeep()
	{
		configFile.update((monitoredFile) -> {
			monitoredFile.path
		});
	}
	
}

package core;
import java.nio.file.Path;

import utils.GlobalLog;
import utils.LogFilter;
import utils.io.FileMonitor;

public class Config
{
	private static final String filepath = Constants.AssetDirectory + Constants.ConfigFilename; 
	FileMonitor configFile;
	
	public static Config instance;
	
	// Accessable config related things
	public LocStrings locStrings;
	public LocCommands locCommands;
	public CommandEnabler commandEnabler;
	
	public Config()
	{
		if(instance == null)
		{
			// Start by reading from things that are external. Because
			// we require these things to be resolved before the rest of the application,
			// we place them here.
			configFile = new FileMonitor(filepath);
			
			commandEnabler = new CommandEnabler();
			locStrings = new LocStrings();
			locCommands = new LocCommands();
			
			instance = this;
		}
		else
		{
			GlobalLog.error(LogFilter.Core, "You can't have two of the following: " + this.getClass().getSimpleName());
			System.exit(-1);
		}
	}
	
}

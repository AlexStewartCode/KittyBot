package core;
import java.nio.file.Path;

import utils.GlobalLog;
import utils.LogFilter;
import utils.io.MonitoredFile;

public class Config
{
	private static final Path filepath = Constants.AssetDirectory + Constants.ConfigFilename; 
	MonitoredFile configFile;
	
	Config instance;
	
	public Config()
	{
		if(instance == null)
		{
			configFile = new MonitoredFile(filepath);
			instance = this;
		}
		else
		{
			GlobalLog.error(LogFilter.Core, "You can't have two of the following: " + this.getClass().getSimpleName());
		}
	}
	
}

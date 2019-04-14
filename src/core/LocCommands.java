package core;

import utils.GlobalLog;
import utils.LogFilter;

public class LocCommands extends LocBase
{
	public static final String fileName = "locCommands.config";
	public static final String function = "LocCommands.Stub";
	
	private static LocCommands instance;
	
	public LocCommands() 
	{
		super(fileName, function);
		
		GlobalLog.Log(LogFilter.Core, "Initializing " + this.getClass().getSimpleName());
		
		if(instance == null)
		{
			instance = this;
			
			UpdateLocFromDisk();
			ScrapeAll();
			SaveLocToDisk();
		}
		else
		{
			GlobalLog.Error(LogFilter.Core, "You can't have two of the following: " + this.getClass().getSimpleName());
		}
	}

	public static String Stub(String toStub)
	{
		return instance.GetKey(toStub);
	}
}

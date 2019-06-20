package core;

import utils.GlobalLog;
import utils.LogFilter;
import utils.io.FileMonitor;

// A quick-and-dirty localization tool that scrapes the project for calls to itself, then
// generates/updates a file externally (phrases.config) with all the stub values as keys that 
// can then be localized.
public class LocStrings extends BaseLocFile
{
	public static final String fileName = "locStrings.config";
	public static final String function = "LocStrings.stub";
	
	private static LocStrings instance;
	
	public LocStrings()
	{
		super(fileName, function);
		
		GlobalLog.Log(LogFilter.Core, "Initializing " + this.getClass().getSimpleName());
		
		if(instance == null)
		{
			instance = this;
			
			updateLocFromDisk();
			scrapeAll();
			saveLocToDisk();
			
			fileMonitor = new FileMonitor(filename);
		}
		else
		{
			GlobalLog.Error(LogFilter.Core, "You can't have two of the following: " + this.getClass().getSimpleName());
		}
	}

	public static String stub(String toStub)
	{
		return lookup(toStub);
	}
	
	// Won't be picked up when scraping
	public static String lookup(String stubbedPreviously)
	{
		return instance.getKey(stubbedPreviously);
	}
	
	public static void upkeep()
	{
		instance.update();
	}
}
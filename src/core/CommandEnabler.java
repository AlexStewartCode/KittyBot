package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import utils.FileUtils;
import utils.GlobalLog;
import utils.LogFilter;

// While some of the patterns in this file are similar to the localization files,
// commands that are being looked up will behave slightly differently so trimming
// rules for this file are different than the localization ones - this is more 
// aggresive with whitespace removal.
public class CommandEnabler
{
	// Config/const variables
	public static final String filename = "commands.config";
	public static final String pairSplit = "=";
	public static final char pairSeparator = '\n';
	public static final String enabled = "on";
	public static final String disabled = "off";
	public static final boolean defaultEnabledState = true;
	
	// Local variables
	private HashMap<String, Boolean> enabledMap; // Quick lookup
	private ArrayList<String> keyList; // Tracking ordering for later
	
	public CommandEnabler()
	{
		enabledMap = new HashMap<>();
		keyList = new ArrayList<>();
		
		ReadIn();
		GetTrackedCommands();
		WriteOut();
	}
	
	// Reads in the config file and parses it, keeping tabs on the order it read things
	private void ReadIn()
	{
		File f = new File(filename);
		if(f.isFile() && f.canRead())
		{
			String content = FileUtils.ReadContent(f);
			String[] lines = content.split("" + pairSeparator);
			
			for(int i = 0; i < lines.length; ++i)
			{
				String[] pair = lines[i].split(pairSplit);
				String key = pair[0].trim();
				String value = pair[0].trim().toLowerCase();
				
				keyList.add(key);
				
				if(value == enabled)
					enabledMap.putIfAbsent(key, true);
				else
					enabledMap.putIfAbsent(key, false);
			}
		}
	}
	
	// Look up the already scraped values from the localizer and store them if they
	// don't already exist in the lookup. Defaults to defaultEnabledState.
	private void GetTrackedCommands()
	{
		ArrayList<String> unloc = LocCommands.GetUnlocalizedCommands();
		
		for(int i = 0; i < unloc.size(); ++i)
			enabledMap.putIfAbsent(unloc.get(i), defaultEnabledState);
	}
	
	// Write out enabled/disabled file info.
	private void WriteOut()
	{
		try
		{
			String outString = "";
			for(int i = 0; i < keyList.size(); ++i)
			{
				String key = keyList.get(i);
				String value = enabled;
				
				if(enabledMap.get(key) == false)
					value = disabled;
				
				outString += key + pairSplit + value + pairSeparator;
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(outString);
			writer.close();
		}
		catch (IOException e)
		{
			GlobalLog.Error(LogFilter.Core, "Command enabler issue writing file! " + e.getMessage());
		}
	}
	
	public boolean IsEnabled(String key)
	{
		if(enabledMap.containsKey(key))
			return enabledMap.get(key);
		
		return true;
	}
}

package core;

import java.util.ArrayList;
import java.util.List;

import dataStructures.KittyStartupMode;
import utils.GlobalLog;

// Want to add a single item to the config file? This is where that lives.
public class ConfigGlobals implements IConfigSection
{
	// Defined const variables
	public static final String header = "Globals";
	public static ConfigGlobals instance;
	
	
	// A pile of variables being manually tracked/serialized to the config file
	private static final String startupStyleKey = "mode";
	private KittyStartupMode startupStyleValue = KittyStartupMode.Development;
	
	// Constructor
	public ConfigGlobals()
	{
		if(instance == null)
			instance = this;
		else
			GlobalLog.error("Attempted to initialize a second config globals! Probably don't do this!");
	}
	
	@Override
	public String getSectionTitle()
	{
		return header;
	}

	public static KittyStartupMode getStartupMode()
	{
		return instance.startupStyleValue;
	}
	
	@Override
	public void consume(List<ConfigItem> pairs)
	{
		for(int i = 0; i < pairs.size(); ++i)
		{
			String key = pairs.get(i).key;
			String value = pairs.get(i).value;
			
			// Parse startup style
			if(key.equalsIgnoreCase(startupStyleKey))
			{
				startupStyleValue = KittyStartupMode.Development;
				
				if(value.equalsIgnoreCase(KittyStartupMode.Release.getValue()))
					startupStyleValue = KittyStartupMode.Release;
			}
		}
	}

	@Override
	public List<ConfigItem> produce()
	{
		List<ConfigItem> items = new ArrayList<ConfigItem>();
		items.add(new ConfigItem(getSectionTitle(), startupStyleKey, startupStyleValue.getValue()));
		
		return items;
	}
}

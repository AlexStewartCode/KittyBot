package core;

import utils.StringUtils;

// Everything in here must be a string type. This class does not
// perform any parsing past naming columns, and just holds the raw data.
public class ConfigItem
{
	public String type;  // The section this specific key/value pair belongs to. This almost certainly will appear in multiple places. 
	public String key;   // A unique key for a given lookup value for the specified section. Keys, within sections, are unique.
	public String value; // The value assocaited with the key.
	public static final int items = 3;
	public static final String[] header = {"Type","Key","Value"};
	
	public ConfigItem(String type, String key, String value)
	{
		this.type = type;
		this.key = key;
		this.value = value;
	}
	
	public String getColumn(int column)
	{
		switch(column)
		{
			case 0: return type; 
			case 1: return key;
			case 2: return value;
			default: return null;
		}
	}
	
	public String[] getAll()
	{
		return new String[] {StringUtils.reEscape(type), StringUtils.reEscape(key), StringUtils.reEscape(value) };
	}
	
	public String toString()
	{
		return "type: " + type + ", key: " + key + ", value: " + value;
	}
}

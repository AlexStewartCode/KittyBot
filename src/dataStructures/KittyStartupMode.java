package dataStructures;

public enum KittyStartupMode 
{
	Development("dev"), Release("release");
	
	private final String value;
	private KittyStartupMode(String value) 
	{
		this.value = value;
	}
	
	public String getValue() 
	{
		return value;
	}
}


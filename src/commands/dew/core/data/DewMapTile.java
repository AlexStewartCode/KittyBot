package commands.dew.core.data;

public enum DewMapTile
{
	Default(0), Tree(1);
	
	private final int value;
	private DewMapTile(int value) 
	{
		this.value = value;
	}
	
	public int getValue() 
	{
		return value;
	}
}

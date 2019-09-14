package commands.dew.core.data;

public enum DewMapTile 
{
	// Assign multiples of 2 for flag purposes
	Default(0), Grass(1), Tree(2);
	
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

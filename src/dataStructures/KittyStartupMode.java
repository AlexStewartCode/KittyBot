package dataStructures;

public enum KittyStartupMode 
{
	Dev(1), Release(2), PoguRelease(3);
	
	private final int value;
	private KittyStartupMode(int value) 
	{
		this.value = value;
	}
	
	public int getValue() 
	{
		return value;
	}
}


package dataStructures;

public enum KittyRating 
{
	Safe(0), Filtered(1), Explicit(2);
	
	private final int value;
	private KittyRating(int value) 
	{
		this.value = value;
	}
	
	public int getValue() 
	{
		return value;
	}
}

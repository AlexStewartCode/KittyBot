package dataStructures;

import java.util.Arrays;
import java.util.Optional;

public enum KittyRole 
{
	Blacklisted (0), General(1), Mod(2), Admin(3), Dev(4);
	
	private final int value;
	private KittyRole(int value) 
	{
		this.value = value;
	}
	
	public int getValue() 
	{
		return value;
	}
	
	public static Optional<KittyRole> valueOf(int value) 
	{
		return Arrays.stream(values()).filter(role -> role.value == value).findFirst();
	}
}

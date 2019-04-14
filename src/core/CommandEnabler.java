package core;

import java.util.HashMap;

public class CommandEnabler
{
	// Config/const variables
	public static final String filename = "commands.config";
	
	// Local variables
	private HashMap<String, Boolean> enabledMap;
	
	public CommandEnabler()
	{
		enabledMap = new HashMap<>();
	}
}

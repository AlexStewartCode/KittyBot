package core.rpg;

import utils.GlobalLog;
import utils.LogFilter;

public class RPGLog 
{
	public static void Log(String toWrite)
	{
		GlobalLog.Log(LogFilter.Command, "[RPG] " + toWrite);
	}
}

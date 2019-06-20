package core.lua;

import utils.GlobalLog;
import utils.LogFilter;

public class PluginLog
{
	public static void log(String s) { GlobalLog.Log(LogFilter.Plugin, s); }
	public static void warn(String s) { GlobalLog.Warn(LogFilter.Plugin, s); }
	public static void error(String s) { GlobalLog.Error(LogFilter.Plugin, s); }
}

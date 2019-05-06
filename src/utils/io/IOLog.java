package utils.io;

import utils.GlobalLog;
import utils.LogFilter;

// Logging shim
public class IOLog
{
	public static void Log(String s) { GlobalLog.Log(LogFilter.Util, s); }
	public static void Warn(String s) { GlobalLog.Warn(LogFilter.Util, s); }
	public static void Error(String s) { GlobalLog.Error(LogFilter.Util, s); }
}

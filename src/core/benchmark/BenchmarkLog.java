package core.benchmark;

import utils.GlobalLog;

//Logging shim
public class BenchmarkLog
{	
	public static void log(String str) { GlobalLog.Log("[Benchmark] " + str); }
	public static void warn(String str) { GlobalLog.Warn("[Benchmark] " + str); }
	public static void error(String str) { GlobalLog.Error(" [Benchmark] " + str); }
}

package core.benchmark;

public abstract class BenchmarkCommand
{
	public BenchmarkCommand()
	{  }
	
	// OVERRIDE ME
	public abstract BenchmarkFormattable OnRun(BenchmarkManager manager, BenchmarkInput input);
}

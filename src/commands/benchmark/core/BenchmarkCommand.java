package commands.benchmark.core;

public abstract class BenchmarkCommand
{
	public BenchmarkCommand()
	{  }
	
	// OVERRIDE ME
	public abstract BenchmarkFormattable onRun(BenchmarkManager manager, BenchmarkInput input);
}

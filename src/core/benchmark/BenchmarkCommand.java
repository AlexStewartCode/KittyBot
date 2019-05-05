package core.benchmark;

public abstract class BenchmarkCommand {
	public BenchmarkCommand() { }
	
	// OVERRIDE ME
	public abstract String OnRun(BenchmarkManager manager, BenchmarkInput input);
}

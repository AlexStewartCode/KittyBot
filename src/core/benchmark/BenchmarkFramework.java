package core.benchmark;

import java.util.HashMap;
import commands.benchmark.*;

// Based on general core structure, this registers sub-commands and keeps tabs on them.
public class BenchmarkFramework
{
	// Variables
	public HashMap<String, BenchmarkCommand> benchmarkCommand;
	public BenchmarkManager benchmarkManager;
	
	// Constructor and command registration
	public BenchmarkFramework()
	{
		this.benchmarkManager = new BenchmarkManager();
		this.benchmarkCommand = new HashMap<String, BenchmarkCommand>();
		
		RegisterCommand("find", new BenchmarkCommandFind());
		RegisterCommand("compare", new BenchmarkCommandCompare());
		RegisterCommand("info", new BenchmarkCommandInfo());
	}
	
	// Runs a command if possible.
	public BenchmarkFormattable Run(String args)
	{
		BenchmarkInput input = new BenchmarkInput(args);
		return ExecuteCommand(input.key, input);
	}
	
	public void Update()
	{
		synchronized(benchmarkManager)
		{
			benchmarkManager.Update();
		}
	}
	
	// Registers a command
	private void RegisterCommand(String commandName, BenchmarkCommand command)
	{
		commandName = commandName.toLowerCase();
		if(benchmarkCommand.put(commandName, command) != null)
			BenchmarkLog.Log("Multiple registration of a command with name '" + commandName + "'!");
		
		BenchmarkLog.Log("Registered " + commandName);
	}
	
	// Executes a command with the specified name, and provides it with some extra input data.
	private BenchmarkFormattable ExecuteCommand(String name, BenchmarkInput input)
	{
		BenchmarkCommand command = benchmarkCommand.get(name.toLowerCase());
		
		if(command != null && benchmarkManager != null)
			return command.OnRun(benchmarkManager,  input);
		
		return null; 
	}
}

package commands;

import core.Command;
import core.LocStrings;
import core.benchmark.BenchmarkFormattable;
import core.benchmark.BenchmarkFramework;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandBenchmark extends Command
{
	// Variables
	private BenchmarkFramework framework;
	
	// Constructor
	public CommandBenchmark(KittyRole level, KittyRating rating)
	{ 
		super(level, rating);
		framework = new BenchmarkFramework();
	}
	
	@Override
	public String getHelpText() { return LocStrings.Stub("BenchmarkInfo"); }
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		framework.Update();
		
		if(input.args == null || input.args.length() == 0)
		{
			String output = getHelpText();
			res.Call(output);
			return;
		}
		
		BenchmarkFormattable commandOutput = null;
		synchronized(framework)
		{
			commandOutput = framework.Run(input.args.trim());
		}
		
		if(commandOutput == null)
		{
			res.Call(LocStrings.Stub("BenchmarkInvalid"));
			return;
		}
		
		commandOutput.Call(res);
	}
}
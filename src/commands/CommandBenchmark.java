package commands;

import core.Command;
import core.LocStrings;
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
	public String HelpText() { return LocStrings.Stub("BenchmarkInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(input.args == null || input.args.length() == 0)
		{
			String output = HelpText();
			res.Call(output);
			return;
		}
		
		String result = null;
		synchronized(framework)
		{
			result = framework.Run(input.args.trim());
		}
		
		if(result == null)
		{
			res.Call(LocStrings.Stub("BenchmarkInvalid"));
			return;
		}
		
		res.Call(result);
	}
}
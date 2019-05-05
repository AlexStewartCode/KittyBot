package commands;

import core.Command;
import core.LocStrings;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandUserBenchmark extends Command
{
	public CommandUserBenchmark(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return LocStrings.Stub("UserBenchmarkInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		String [] info = input.args.split(" ");
		
		switch(info[1].toLowerCase())
		{
			case "cpu":
			break;
			
			case "gpu":
			break;
			
			case "ram":
			break;
			
			case "USB":
			break;
			
			default:
			break;
		}
	}
}
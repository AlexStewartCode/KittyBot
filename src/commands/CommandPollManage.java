package commands;

import core.*;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandPollManage extends Command 
{
	public CommandPollManage(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("PollManageInfo"); }
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		switch(input.args.split(" ")[0].toLowerCase())
		{
		case "start":
			res.call(guild.startPoll(input.args.substring(input.args.indexOf(' ')).trim()));
			break;
			
		case "choice":
			res.call(guild.addChoiceToPoll(input.args.substring(input.args.indexOf(' ')).trim()));
			break;
			
		case "stop":
			res.call(guild.endPoll());
			break; 
		
		default:
			break;
		}
	}
}

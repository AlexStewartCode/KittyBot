package commands;

import core.*;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandChoose extends Command
{
	public CommandChoose(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("ChooseInfo"); }
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(input.args.trim().length() == 0)
		{
			res.call(Stats.instance.GetHelpText(input.key));
			return;
		}
		
		String [] choices = input.args.split(",");
		
		if(choices.length == 1)
		{
			res.call(LocStrings.stub("ChooseOne"));
			return;
		}
		
		res.call(String.format(LocStrings.stub("ChooseChoice"), (choices[(int) (Math.random()*choices.length)]).toString()));
	}
}

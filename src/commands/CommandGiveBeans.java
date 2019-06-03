package commands;

import core.Command;
import core.LocStrings;
import dataStructures.*;

public class CommandGiveBeans extends Command
{
	public CommandGiveBeans (KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return LocStrings.Stub("GiveBeansInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		// First, make sure someone is mentioned
		if(input.mentions == null)
		{
			res.Call(LocStrings.Stub("GiveBeansNoneMentioned"));
			return;
		}
		
		// If there are mentions, try and find a number.
		Integer beans = null;
		String[] split = input.args.split(" ");
		
		// Find the first parseable number in the split string
		for(int i = 0; i < split.length; ++i)
		{
			try
			{
				beans = Integer.parseInt(split[i]);
				break;
			}
			catch (NumberFormatException e)
			{
				continue;
			}
		}
		
		// If there wasn't a number we could find, well, nothing we can do.
		if(beans == null)
		{
			res.Call(LocStrings.Stub("GiveBeansInvalid"));
			return;
		}
		
		// Go through all the mentions and make sure every user is given beans!
		for(int i = 0; i < input.mentions.length; i++)
		{
			input.mentions[i].ChangeBeans(beans);
			res.Call(String.format(LocStrings.Stub("GiveBeansSuccess"), input.mentions[i].name, "" + beans));
		}
	}
}

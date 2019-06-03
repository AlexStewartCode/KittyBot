package commands;

import core.Command;
import core.LocStrings;
import dataStructures.*;

public class CommandBeansShow extends Command
{
	public CommandBeansShow(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.Stub("BeansShowInfo"); };
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(input.mentions == null)
			res.Call(String.format(LocStrings.Stub("BeansShowDisplay"), user.GetBeans()));
		else
		{
			String mentionedBeans = ""; 
			for(KittyUser mentioned:input.mentions)
			{
				mentionedBeans += mentioned.name + " has " + mentioned.GetBeans() + " ";
			}
			res.Call(String.format(LocStrings.Stub("BeansShowMentioned"), mentionedBeans));
		}
	}
}

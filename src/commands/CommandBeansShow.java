package commands;

import core.Command;
import dataStructures.*;

public class CommandBeansShow extends Command
{
	public CommandBeansShow(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return "Displays how many beans you have"; };
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		String beans = "You have " + user.GetBeans() + " beans!";
		res.Call(beans);
	}
}

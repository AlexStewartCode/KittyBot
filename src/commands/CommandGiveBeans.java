package commands;

import core.Command;
import dataStructures.*;

public class CommandGiveBeans extends Command
{
	public CommandGiveBeans (KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return "Gives beans to the mentioned users!"; }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		int beans = 0;
		try {
			beans = Integer.parseInt(input.args.split(" ")[0]);
		}
		catch (NumberFormatException e)
		{
			res.Call("That's not a valid number!");
			return;
		}
		
		if(input.mentions == null)
		{
			res.Call("You didn't mention anyone!");
			return;
		}
		
		for(int i = 0; i < input.mentions.length; i++)
		{
			input.mentions[i].ChangeBeans(beans);
			res.Call("Gave " + input.mentions[i].name + " " + beans + " beans!");
		}
	}
}

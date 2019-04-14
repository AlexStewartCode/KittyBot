package commands;

import core.*;
import dataStructures.*;

public class CommandPollShow extends Command
{
	public CommandPollShow(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return Localizer.Stub("PollShowInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(!guild.polling)
		{
			res.Call(Localizer.Stub("PollShowNoPoll"));
			return;
		}
		String poll = String.format(Localizer.Stub("PollShowPoll"), guild.poll);
		poll += Localizer.Stub("PollShowChoices");
		for(int i = 0; i < guild.choices.size(); i++)
		{
			poll += (i+1) + ": `" + guild.choices.get(i).choice + "`\n";
		}
		
		res.Call(poll);
	}
}

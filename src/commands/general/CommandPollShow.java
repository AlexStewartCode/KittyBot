package commands.general;

import core.*;
import dataStructures.*;

public class CommandPollShow extends Command
{
	public CommandPollShow(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("PollShowInfo"); }
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(!guild.polling)
		{
			res.send(LocStrings.stub("PollShowNoPoll"));
			return;
		}
		String poll = String.format(LocStrings.stub("PollShowPoll"), guild.poll);
		poll += LocStrings.stub("PollShowChoices");
		for(int i = 0; i < guild.choices.size(); i++)
		{
			poll += (i+1) + ": `" + guild.choices.get(i).choice + "`\n";
		}
		
		res.send(poll);
	}
}

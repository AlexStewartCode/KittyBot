package commands;

import core.*;
import dataStructures.*;

public class CommandPollVote extends Command
{
	public CommandPollVote(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return Localizer.Stub("PollVoteInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(guild.polling)
		{
			if(guild.hasVoted.contains(user.uniqueID))
			{
				res.Call(Localizer.Stub("PollVoteAlreadyVoted"));
				return;
			}
			try 
			{
				int voteNum = Integer.parseInt(input.args)-1;
				if(voteNum >= guild.choices.size() || voteNum < 0)
				{
					res.Call(String.format(Localizer.Stub("PollVoteNotValidVote"), voteNum));
					return;
				}
				
				KittyPoll polled = guild.choices.get(voteNum);
				polled.votes++;
				guild.hasVoted.add(user.uniqueID);
				res.Call(Localizer.Stub("PollVoteSuccess") + " `" + polled.choice + "`!");
				return;
			}
			catch (NumberFormatException e)
			{
				res.Call(Localizer.Stub("PollVoteNotValidNumber"));
				return;
			}
		}
		
		res.Call(Localizer.Stub("PollVoteNoPoll"));
	}
}

package commands;

import core.*;
import dataStructures.*;

public class CommandPollVote extends Command
{
	public CommandPollVote(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return "Vote in a poll with the choice number, won't work if no poll is running, you can't change your vote once you have cast it! Be careful!"; }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(guild.polling)
		{
			if(guild.hasVoted.contains(user.uniqueID))
			{
				res.Call("You already voted");
				return;
			}
			try 
			{
				int voteNum = Integer.parseInt(input.args)-1;
				if(voteNum >= guild.choices.size() || voteNum < 0)
				{
					res.Call(voteNum + " That's not a vaild vote!");
					return;
				}
				KittyPoll polled = guild.choices.get(voteNum);
				polled.votes++;
				guild.hasVoted.add(user.uniqueID);
				res.Call("You successfully voted for `" + polled.choice + "`!");
				return;
			}
			catch (NumberFormatException e)
			{
				res.Call("That's not a vaild number!");
				return;
			}
		}
		res.Call("There is no poll running!");
	}
}

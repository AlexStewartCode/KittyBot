package commands;

import core.Command;
import core.LocStrings;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandRaffleJoin extends Command
{
	public CommandRaffleJoin(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("RaffleJoinInfo"); }
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(guild.joinRaffle(user))
		{
			res.send(LocStrings.stub("RaffleJoinSuccess"));
		}
		else
		{
			res.send(LocStrings.stub("RaffleJoinFailure"));
		}
	}
}

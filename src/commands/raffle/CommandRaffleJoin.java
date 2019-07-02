package commands.raffle;

import core.LocStrings;
import core.SubCommand;
import core.SubCommandFormattable;
import dataStructures.*;

public class CommandRaffleJoin extends SubCommand
{
	public CommandRaffleJoin(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, String input)
	{
		if(guild.joinRaffle(user))
		{
			return new SubCommandFormattable (LocStrings.stub("RaffleJoinSuccess"));
		}
		else
		{
			return new SubCommandFormattable (LocStrings.stub("RaffleJoinFailure"));
		}
	}
}

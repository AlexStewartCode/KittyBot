package commands.raffle;

import core.LocStrings;
import core.SubCommand;
import core.SubCommandFormattable;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;

public class CommandRaffleEnd extends SubCommand
{
	public CommandRaffleEnd(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, String input)
	{
		if(guild.endRaffle())
		{
			return new SubCommandFormattable (LocStrings.stub("RaffleEndSuccess"));
		}
		else
		{
			return new SubCommandFormattable (LocStrings.stub("RaffleEndFailure"));
		}
	}
}
package commands.raffle;

import core.LocStrings;
import core.SubCommand;
import core.SubCommandFormattable;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;

public class CommandRaffleSpin extends SubCommand
{
	public CommandRaffleSpin(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, String input)
	{
		try
		{
			return new SubCommandFormattable (String.format(LocStrings.stub("RaffleSpinSuccess"), guild.chooseRaffleWinner().name));
		}
		catch(Exception e)
		{
			return new SubCommandFormattable (LocStrings.stub("RaffleSpinFailure"));
		}
	}
}
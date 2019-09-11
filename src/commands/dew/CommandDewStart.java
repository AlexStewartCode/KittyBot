package commands.dew;

import core.SubCommand;
import core.SubCommandFormattable;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.UserInput;

public class CommandDewStart extends SubCommand
{
	public CommandDewStart(KittyRole roleLevel, KittyRating contentRating)
	{
		super(roleLevel, contentRating);
	}

	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input) 
	{
		return new SubCommandFormattable ("weh");
	}
}
package commands.dew;

import commands.dew.adapter.KittyAdapterDewPlayer;
import commands.dew.core.impl.DewCore;
import core.SubCommand;
import core.SubCommandFormattable;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.UserInput;

public class CommandDewCapture extends SubCommand
{
	public CommandDewCapture(KittyRole roleLevel, KittyRating contentRating)
	{
		super(roleLevel, contentRating);
	}

	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input)
	{
		KittyAdapterDewPlayer player = DewCore.getOrCreate(user);
		DewCore.playersCaptured.add(player);
		return null;//new SubCommandFormattable("captured input for " + user.name);
	}
}

package commands.dew;

import core.SubCommand;
import core.SubCommandFormattable;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.UserInput;

public class CommandDewInput extends SubCommand
{
	public CommandDewInput(KittyRole roleLevel, KittyRating contentRating) {
		super(roleLevel, contentRating);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input) {
		// TODO Auto-generated method stub
		return null;
	}

}

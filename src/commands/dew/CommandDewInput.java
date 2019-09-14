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

public class CommandDewInput extends SubCommand
{
	String letter = "";
	public CommandDewInput(String letter, KittyRole roleLevel, KittyRating contentRating)
	{
		super(roleLevel, contentRating);
		this.letter = letter.toLowerCase();
	}

	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input)
	{
		KittyAdapterDewPlayer player = DewCore.getOrCreate(user);
		
		int x = player.getRealmX();
		int y = player.getRealmY();
		
		switch(letter)
		{
			case "w": player.setRealmPos(x, y - 1); break;
			case "a": player.setRealmPos(x - 1, y); break;
			case "s": player.setRealmPos(x, y + 1); break;
			case "d": player.setRealmPos(x + 1, y); break;
		}
		
		return new SubCommandFormattable(DewCore.drawWorld(player));
	}
}

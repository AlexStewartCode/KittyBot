package commands.dew;

import commands.dew.core.data.*;
import commands.dew.core.impl.DewLuaCore;
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

	private String asCodeBlock(String string)
	{
		return "```lua\n" + string + "```"; 
	}
	
	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input) 
	{
		String out = "";
		
		out += asCodeBlock(DewLuaCore.toLua(new DewMap()));
		out += asCodeBlock(DewLuaCore.toLua(new DewMovementInfo()));
		
		return new SubCommandFormattable(out);
	}
}

package commands.dew;

import java.util.UUID;

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

public class CommandDewLuaTest extends SubCommand
{
	public CommandDewLuaTest(KittyRole roleLevel, KittyRating contentRating)
	{
		super(roleLevel, contentRating);
	}

	private String asCodeBlock(String string)
	{
		return "```lua\n" + string + "```"; 
	}
	
	private String asCodeLine(String string)
	{
		return "`" + string + "`"; 
	}
	
	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input) 
	{
		String out = "";
		
		try
		{
			out += "**Testing writing...\n**";
			DewRealm m = new DewRealm();
			m.id = UUID.randomUUID().toString();
			String out1 = DewLuaCore.toLua(m);
			out += asCodeBlock(out1);
			
			String out2 = DewLuaCore.toLua(new DewMovementInfo());
			out += asCodeBlock(out2);
			
			out += "**Testing Reading...**\n";
			
			DewRealm map = new DewRealm();
			DewLuaCore.fromLua(map, out1);
			out += "ID from 1st write: " + asCodeLine(map.id) + "\n";
			
			DewMovementInfo move = new DewMovementInfo();
			DewLuaCore.fromLua(move, out2);
			out += "Qualified type test: " + asCodeLine(move.toString()) + "\n";
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
		}
		
	
		return new SubCommandFormattable(out);
	}
}

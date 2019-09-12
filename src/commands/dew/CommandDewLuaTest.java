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
			DewMap m = new DewMap();
			m.mapHeight = 25;
			String out1 = DewLuaCore.toLua(m);
			out += asCodeBlock(out1);
			
			String out2 = DewLuaCore.toLua(new DewMovementInfo());
			out += asCodeBlock(out2);
			
			out += "**Testing Reading...**\n";
			
			DewMap map = new DewMap();
			DewLuaCore.fromLua(map, out1);
			out += "Height from 1st write: " + map.mapHeight + "\n";
			
			DewMovementInfo move = new DewMovementInfo();
			DewLuaCore.fromLua(move, out2);
			out += move + "\n";
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
		}
		
	
		return new SubCommandFormattable(out);
	}
}

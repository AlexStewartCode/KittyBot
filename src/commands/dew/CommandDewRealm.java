package commands.dew;

import commands.dew.core.data.DewMapTile;
import commands.dew.core.data.DewRealm;
import core.SubCommand;
import core.SubCommandFormattable;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.UserInput;

public class CommandDewRealm  extends SubCommand
{
	DewRealm realm;
	public CommandDewRealm(KittyRole roleLevel, KittyRating contentRating)
	{
		super(roleLevel, contentRating);
		realm = new DewRealm();
		
		for(int y = 0; y < realm.map.length; ++y)
		{
			for(int x = 0; x < realm.map[y].length; ++x)
			{
				realm.map[y][x] = DewMapTile.Grass.getValue();
			}
		}
	}

	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input)
	{
		String out = "";
		out += "**Realm ID:** `" + realm.id + "`\n";
		
		for(int x = 0; x < realm.map.length; ++x)
		{
			out += "`";
			for(int y = 0; y < realm.map[x].length; ++y)
			{
				switch(DewMapTile.values()[realm.map[x][y]])
				{
					case Grass:
						out += ",";
						break;
						
					default:
						break;
						
				}
			}
			out += "`";
			out += "\n";
		}
		
		return new SubCommandFormattable(out);
	}

}

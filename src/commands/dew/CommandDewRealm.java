package commands.dew;

import java.util.ArrayList;
import java.util.List;

import commands.dew.adapter.KittyAdapterDewPlayer;
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
	// TODO: CHANGE THIS! THIS IS TEMP STATIC, DO NOT CONTINUE TO DO THIS
	private static DewRealm realm;
	public static List<KittyAdapterDewPlayer> players;
	
	public CommandDewRealm(KittyRole roleLevel, KittyRating contentRating)
	{
		super(roleLevel, contentRating);
		
		realm = new DewRealm();
		players = new ArrayList<KittyAdapterDewPlayer>();
		
		for(int y = 0; y < realm.map.length; ++y)
		{
			for(int x = 0; x < realm.map[y].length; ++x)
			{
				realm.map[y][x] = DewMapTile.Grass.getValue();
			}
		}
	}

	public static KittyAdapterDewPlayer getOrCreate(KittyUser user)
	{
		for(KittyAdapterDewPlayer player : players)
		{
			if(player.getUniqueID() == user.uniqueID)
			{
				return player;
			}
		}
		
		KittyAdapterDewPlayer player = new KittyAdapterDewPlayer(realm, user, 10, 10);
		players.add(player);
		return player;
	}
	
	public static String drawWorld(KittyAdapterDewPlayer player)
	{
		// Build empty world buffer
		String[][] worldBuffer = new String[realm.map.length][realm.map[0].length];
		
		// Build world
		for(int y = 0; y < realm.map.length; ++y)
		{
			for(int x = 0; x < realm.map[y].length; ++x)
			{
				switch(DewMapTile.values()[realm.map[y][x]])
				{
					case Grass:
						worldBuffer[y][x] = ",";
						break;
						
					default:
						worldBuffer[y][x] = " ";
						break;
				}
			}
		}		
		
		// Build players
		for(KittyAdapterDewPlayer p : players)
		{
			worldBuffer[p.getRealmY()][p.getRealmX()] = p.getVisual();
		}
		
		// Draw world
		String out = "";
		out += "**Realm ID - ** `" + realm.id + "`\n";
		
		for(int y = 0; y < worldBuffer.length; ++y)
		{
			out += "`";
			
			for(int x = 0; x < worldBuffer[y].length; ++x)
			{
				out += worldBuffer[y][x];
			}
			
			out += "`";
			out += "\n";
		}
		
		out += "**Stats**\n";
		out += "`Player Name` - `" + player.getName() + "`\n";
		out += "`Player Icon` - `" + player.getVisual() + "`\n";
		
		return out;
	}
	
	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input)
	{
		KittyAdapterDewPlayer player = getOrCreate(user);
		
		String out = drawWorld(player);
		
		return new SubCommandFormattable(out);
	}

}

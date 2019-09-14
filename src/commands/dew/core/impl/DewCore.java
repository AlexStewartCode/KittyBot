package commands.dew.core.impl;

import java.util.ArrayList;
import java.util.List;

import commands.dew.adapter.KittyAdapterDewPlayer;
import commands.dew.core.data.DewMapTile;
import commands.dew.core.data.DewRealm;
import core.SubCommandFormattable;
import core.SubCommandFramework;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;
import utils.GlobalLog;
import utils.LogFilter;

public class DewCore 
{
	// TODO: Make realm and players both per-realm
	public static DewRealm realm;
	public static List<KittyAdapterDewPlayer> players;
	public static List<KittyAdapterDewPlayer> playersCaptured;
	private static SubCommandFramework framework;
	
	public DewCore(SubCommandFramework framework)
	{
		DewCore.framework = framework;
		DewCore.realm = new DewRealm();
		DewCore.players = new ArrayList<KittyAdapterDewPlayer>();
		DewCore.playersCaptured = new ArrayList<KittyAdapterDewPlayer>();
		
		for(int y = 0; y < realm.map.length; ++y)
		{
			for(int x = 0; x < realm.map[y].length; ++x)
			{
				realm.map[y][x] = DewMapTile.Grass.getValue();
			}
		}
	}
	
	// Returns false if not captured, true if captured.
	public static boolean ProcessIfCaptured(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput baseInput, Response res)
	{
		// Search for a captured player.
		KittyAdapterDewPlayer player = null;
		for(KittyAdapterDewPlayer p : playersCaptured)
		{
			if(p.getUniqueID() == user.uniqueID)
			{
				player = p;
				break;
			}
		}
		
		// If we didn't find that the person is captured, return false since we don't need to do anything.
		if(player == null)
			return false;
		
		// See if we run anything - also, check to see if we need to exit.
		boolean didRun = false;
		try
		{
			UserInput toEdit = new UserInput(baseInput);
			String key = toEdit.message.toLowerCase().trim();
			toEdit.key = key;
			toEdit.args = key;
			switch(key)
			{
				case "cap":
				case "capture":
				case "uncapture":
				case "stop":
				case "quit":
				case "exit":
					playersCaptured.remove(player);
					return false;
			}
			
			SubCommandFormattable f = framework.run(guild, channel, user, toEdit);
			
			if(f != null)
			{
				Response.EditLastMessage(f.resString);
				didRun = true;
			}
		}
		catch(Exception e)
		{
			GlobalLog.error(LogFilter.Command, e);
		}
		
		if(didRun)
		{
			baseInput.deleteOriginInput();
		}
		
		return didRun;
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
}

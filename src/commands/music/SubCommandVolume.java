package commands.music;

import core.LocStrings;
import core.SubCommand;
import core.SubCommandFormattable;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.UserInput;

public class SubCommandVolume extends SubCommand
{
	public SubCommandVolume(KittyRole level, KittyRating rating) { super(level, rating); }
	{
		
	}
	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input) 
	{
		int newVol = guild.audio.getVolume(guild.audio.player);
		if(input.args != null)
		{
			try
			{
				newVol = Integer.parseInt(input.args);
				if(newVol > 100 || newVol <1)
				{
					return new SubCommandFormattable(String.format(LocStrings.stub("MusicVolOOB")));
				}
				guild.audio.changeVolume(guild.audio.player, newVol);
			}
			catch(Exception e)
			{
				return new SubCommandFormattable(String.format(LocStrings.stub("MusicVolCurrent"), newVol));
			}
			
		}
		return new SubCommandFormattable(String.format(LocStrings.stub("MusicVolChanged"), newVol));
	}
}
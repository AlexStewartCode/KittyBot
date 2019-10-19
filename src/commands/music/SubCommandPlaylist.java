package commands.music;

import java.util.ArrayList;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import core.SubCommand;
import core.SubCommandFormattable;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.UserInput;

public class SubCommandPlaylist extends SubCommand 
{

	public SubCommandPlaylist(KittyRole roleLevel, KittyRating contentRating) {super(roleLevel, contentRating);	}

	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input) 
	{
		ArrayList <AudioTrack> playlist = guild.audio.getPlaylist();
		String stringPlaylist = "";
		
		for(AudioTrack track : playlist)
		{
			stringPlaylist += (playlist.indexOf(track) + 1) + ": " + track.getInfo().title + "\n"; 
		}
		if(stringPlaylist.equals(""))
		{
			return new SubCommandFormattable("Nothing Queued!");
		}
		return new SubCommandFormattable(stringPlaylist);
	}

}

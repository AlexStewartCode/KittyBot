package commands.music;

import core.SubCommand;
import core.SubCommandFormattable;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.UserInput;
import network.NetworkYoutube;
import utils.audio.AudioController;

public class SubCommandAddTrack extends SubCommand 
{
	
	public SubCommandAddTrack(KittyRole level, KittyRating rating) { super(level, rating); }
	
	NetworkYoutube YT = new NetworkYoutube();
	AudioController ac = new AudioController();
	
	@Override
	public SubCommandFormattable OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input)
	{
		//Get track from Youtube 
		String video = YT.getYT(input.args);
		ac.loadAndPlay(video, guild);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		return new SubCommandFormattable(ac.response);
	}
}
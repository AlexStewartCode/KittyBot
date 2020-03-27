package utils.audio;
import java.util.ArrayList;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

import net.dv8tion.jda.core.audio.AudioSendHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

public class AudioUtils 
{
	private Guild guild;
	public AudioManager am;
	public final AudioPlayer player;
	public final AudioPlayerManager playerManager;
	private ArrayList <AudioTrack> playlist = new ArrayList<AudioTrack>();
	
	
	public AudioUtils(Guild guild, AudioPlayerManager playerManager)
	{
		player = playerManager.createPlayer();
		this.playerManager = playerManager;
		this.guild = guild;
		am = guild.getAudioManager();
		
	}
	
	public boolean joinChannel(String channelName)
	{
		VoiceChannel channel = null; 
		channel = guild.getVoiceChannelsByName(channelName, true).get(0);
		if(channel != null)
		{
			am.openAudioConnection(channel);
			return true;
		}
		return false; 
	}
	
	public boolean leaveChannel()
	{
		if(am.isConnected())
		{
			am.closeAudioConnection();
			return true;
		}
		return false;
	}
	
	
	public class AudioPlayerSendHandler implements AudioSendHandler 
	{
		private final AudioPlayer audioPlayer;
		private AudioFrame lastFrame;
		
		public AudioPlayerSendHandler(AudioPlayer audioPlayer) 
		{
			this.audioPlayer = audioPlayer;
		}
		
		@Override
		public boolean canProvide() 
		{
			lastFrame = audioPlayer.provide();
			return lastFrame != null;
		}
		
		@Override public byte[] provide20MsAudio()
		{
			return lastFrame.getData();
		}
		
		@Override public boolean isOpus()
		{
			return true;
		}
	}
	
	public class SmallAudio implements AudioLoadResultHandler
	{	
		private SmallAudio(AudioPlayer player)
		{
		}
		
		@Override public void loadFailed(FriendlyException arg0) { }
		@Override public void noMatches() { }
		@Override public void playlistLoaded(AudioPlaylist arg0) { }
		
		@Override
		public void trackLoaded(AudioTrack track)
		{
			synchronized(playlist)
			{
				playlist.add(track);
			}
		}
	}
	
	
	public String playVideo(String videoURL)
	{
		guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
		playerManager.loadItemOrdered(player, videoURL, new SmallAudio(player));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		if(player.getPlayingTrack() == null)
		{
			MusicController control = new MusicController(player, playlist);
			control.start();
		}
		return null;
	}
	
	public int getVolume(AudioPlayer player)
	{
		return player.getVolume();
	}
	
	public void changeVolume(AudioPlayer player, int newVol)
	{
		player.setVolume(newVol);
	}
	
	public String skipVideo(AudioPlayer player)
	{
		player.stopTrack();
		return "Skipped";
	}
	
	public String stopPlayer(AudioPlayer player)
	{
		playlist.clear();
		player.stopTrack();
		return "Stopped";
	}
	
	public ArrayList<AudioTrack> getPlaylist()
	{
		return playlist;
	}
}

class MusicController extends Thread
{
	AudioPlayer player;
	private ArrayList <AudioTrack> playlist;
	
	public MusicController(AudioPlayer player, ArrayList <AudioTrack> playlist)
	{
		this.player = player;
		this.playlist = playlist; 
	}
	
	@Override
	public void run()
	{
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {}
		AudioTrack nowPlaying = null;
		do 
		{
			if(!playlist.isEmpty() || nowPlaying != null)
			{
				if(player.getPlayingTrack() == null)
				{
					if(playlist.isEmpty())
						nowPlaying = null;
					else
					{
						nowPlaying = playlist.get(0);
						playlist.remove(0);
					}
					player.startTrack(nowPlaying, false);
				}
			}
		}
		while(!playlist.isEmpty() || player.getPlayingTrack() != null);
	}
}

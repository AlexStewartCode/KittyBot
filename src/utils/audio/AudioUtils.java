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
	private boolean isPlaying = false; 
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
		private AudioPlayer player;
		
		private SmallAudio(AudioPlayer player)
		{
			this.player = player;
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
			
			if(player.getPlayingTrack() == null)
			{
				startPlay(player);
			}
		}
	}
	
	
	public String playVideo(String videoURL)
	{
		guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
		playerManager.loadItemOrdered(player, videoURL, new SmallAudio(player));
		
		
		return null;
	}
	
	private void startPlay(AudioPlayer player)
	{
		
		do 
		{
			if(player.getPlayingTrack() == null)
			{
				System.out.println("HERE");
				if(playlist.isEmpty())
				{
					isPlaying = false;
				}
				else
				{
					System.out.println("NEXT SONG");
					player.startTrack(playlist.get(0), false);
					isPlaying = true;
					
					if(!playlist.isEmpty())
						playlist.remove(0);
				}
			}
		}
		while(isPlaying);
		isPlaying = false;
	}
	
	public String skipVideo(AudioPlayer player)
	{
		player.stopTrack();
		try 
		{
			playlist.remove(0);
			player.getPlayingTrack();
		}
		catch(Exception e)
		{
			return "Stopped";
		}
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

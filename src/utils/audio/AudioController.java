package utils.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import dataStructures.KittyGuild;

public class AudioController 
{
	public String response;
	private final AudioPlayerManager playerManager;
	
	
	public AudioController()
	{
		playerManager = new DefaultAudioPlayerManager();
	    AudioSourceManagers.registerRemoteSources(playerManager);
	    AudioSourceManagers.registerLocalSource(playerManager);
	}
	
	public void loadAndPlay(final String trackUrl, KittyGuild guild) 
	{
		synchronized(response)
		{
			GuildMusicManager musicManager = guild.musicManager;

		    playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() 
		    {
		      @Override
		      public void trackLoaded(AudioTrack track) 
		      {
		        response = "Adding to queue " + track.getInfo().title;
		        System.out.println("QUEUED");
		        play(musicManager, track);
		      }

		      @Override
		      public void playlistLoaded(AudioPlaylist playlist) 
		      {
		    	AudioTrack firstTrack = playlist.getSelectedTrack();

		    	if (firstTrack == null) 
		    	{
		    		  firstTrack = playlist.getTracks().get(0);
		    	}

		    	response = "Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")";

		    	play(musicManager, firstTrack);
		      }

		      @Override
		      public void noMatches() 
		      {
		    	  System.out.println("NOT QUEUED");
		    	  response = "Nothing found by " + trackUrl;
		      }

		      @Override
		      public void loadFailed(FriendlyException exception) 
		      {
		    	  System.out.println("EXCEPTION");
		    	  System.out.println(exception.getCause());
		      }
		    });
		}
	    
	  }

	  private void play(GuildMusicManager musicManager, AudioTrack track) 
	  {
		  
		  musicManager.scheduler.queue(track);
	  }

	  private void skipTrack(KittyGuild guild) 
	  {
		    GuildMusicManager musicManager = guild.musicManager;
		    musicManager.scheduler.nextTrack();
	
		    response = "Skipped to next track.";
	  }
}

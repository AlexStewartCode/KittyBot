package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import core.CharacterManager;
import core.CommandEnabler;
import core.CommandManager;
import core.DatabaseManager;
import core.ObjectBuilderFactory;
import core.RPManager;
import core.Stats;
import core.lua.PluginManager;
import dataStructures.KittyChannel;
import dataStructures.KittyCore;
import dataStructures.KittyGuild;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;
import offline.Ref;
import trash.GuildMusicManager;
import utils.GlobalLog;

// http://www.slf4j.org/ - this JDA logging tool has been disabled by specifying NOP implementation.
// This is the application entry point, and bot startup location!

@SuppressWarnings("unused")
public class Main extends ListenerAdapter
{
	// Variables and bot specific objects
	private static KittyCore kittyCore;
	private static DatabaseManager databaseManager; 
	private static CommandManager commandManager;
	private static Stats stats;
	private static RPManager rpManager;
	private static PluginManager pluginManager;
	private static CharacterManager charManager; 
	
	
	// Initialization and setup
	public static void main(String[] args) throws InterruptedException, LoginException, Exception
	{
	    JDA bot = new JDABuilder(AccountType.BOT).setToken(Ref.TestToken).buildBlocking();
	    bot.addEventListener(new Main());
	    
	    
	}
	
		  private final AudioPlayerManager playerManager;
		  private final Map<Long, GuildMusicManager> musicManagers;
		  
		  public Main() {
		    this.musicManagers = new HashMap<>();
		    
		    this.playerManager = new DefaultAudioPlayerManager();
		    AudioSourceManagers.registerRemoteSources(playerManager);
		    AudioSourceManagers.registerLocalSource(playerManager);
		  }
		  
		  private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
		    long guildId = Long.parseLong(guild.getId());
		    GuildMusicManager musicManager = musicManagers.get(guildId);
		    
		    if (musicManager == null) {
		      musicManager = new GuildMusicManager(playerManager);
		      musicManagers.put(guildId, musicManager);
		    }
		    
		    guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
		    
		    return musicManager;
		  }
		  
		  @Override
		  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		    String[] command = event.getMessage().getContentRaw().split(" ", 2);
		    
		    if ("!play".equals(command[0]) && command.length == 2) {
		      loadAndPlay(event.getChannel(), command[1]);
		    } else if ("!skip".equals(command[0])) {
		      skipTrack(event.getChannel());
		    }
		    
		    super.onGuildMessageReceived(event);
		  }
		  
		  private void loadAndPlay(final TextChannel channel, final String trackUrl) {
		    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
		    
		    playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
		      @Override
		      public void trackLoaded(AudioTrack track) {
		        channel.sendMessage("Adding to queue " + track.getInfo().title).queue();

		        play(channel.getGuild(), musicManager, track);
		      }

		      @Override
		      public void playlistLoaded(AudioPlaylist playlist) {
		        AudioTrack firstTrack = playlist.getSelectedTrack();

		        if (firstTrack == null) {
		          firstTrack = playlist.getTracks().get(0);
		        }

		        channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

		        play(channel.getGuild(), musicManager, firstTrack);
		      }

		      @Override
		      public void noMatches() {
		        channel.sendMessage("Nothing found by " + trackUrl).queue();
		      }

		      @Override
		      public void loadFailed(FriendlyException exception) {
		    	  System.out.println(exception.getCause());
		        channel.sendMessage("Could not play: " + exception.getMessage()).queue();
		      }
		    });
		  }

		  private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
		    connectToFirstVoiceChannel(guild.getAudioManager());

		    musicManager.scheduler.queue(track);
		  }

		  private void skipTrack(TextChannel channel) {
		    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
		    musicManager.scheduler.nextTrack();

		    channel.sendMessage("Skipped to next track.").queue();
		  }

		  private static void connectToFirstVoiceChannel(AudioManager audioManager) {
		    if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
		      for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
		        audioManager.openAudioConnection(voiceChannel);
		        break;
		      }
		    }
		  }
}
		  
		  
		/* ----------------------------------------------------------------------------------------------------------------------------------------------------------
		 * 
		 * 
		// Factory startup. The ordering is intentional.
		GlobalLog.initialize();
		databaseManager = ObjectBuilderFactory.constructDatabaseManager();
		commandManager = ObjectBuilderFactory.constructCommandManager(CommandEnabler.instance); // TODO: Untangle this singleton
		stats = ObjectBuilderFactory.constructStats(commandManager);
		charManager = new CharacterManager();
		rpManager = ObjectBuilderFactory.constructRPManager();
		pluginManager = ObjectBuilderFactory.constructPluginManager();
		
		// Bot startup
		kittyCore = ObjectBuilderFactory.constructKittyCore();
	}

	// When a message is sent in a server that kitty is in, this is what's called.
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		// Tweak the event as necessary
		if(!Superintendent.preProcessSetup(event, stats))
			return;
		
		// Factory objects
		KittyUser user = ObjectBuilderFactory.extractUser(event);
		KittyGuild guild = ObjectBuilderFactory.extractGuild(event);
		KittyChannel channel = ObjectBuilderFactory.extractChannel(event);
		
		// Specialized uncached objects
		Response response = new Response(event, kittyCore);
		UserInput input = new UserInput(event, guild);
		
		// Tweak object construction as necessary
		if(!Superintendent.postProcessSetup(event, user, guild, channel, response, input))
			return;
				
		// Track beans!
		user.changeBeans(1);
		
		// RP logging system
		RPManager.instance.addLine(channel, user, input);
		
		// Run any pre-emptive upkeep we need to
		if(!Superintendent.perCommandUpkeepPre(pluginManager))
			return;
		
		// Attempt to spin up a command. If the command doesn't exist.
		// Run plugins right before invoking the commands but after all other setup.
		if(commandManager.invokeOnNewThread(guild, channel, user, input, response) == false)
		{
			List<String> pluginOutput = pluginManager.runAll(input.message, user);
			
			if(pluginOutput != null && pluginOutput.size() > 0)
			{
				for(int i = 0; i < pluginOutput.size(); ++i)
				{
					if(pluginOutput.get(i).startsWith("!"))
					{
						commandManager.invokeOnNewThread(guild, channel, user, new UserInput(pluginOutput.get(i).substring(1), guild), response);
					}
					else
					{
						response.send(pluginOutput.get(i));
					}
				}
			}
		}
		
		// Run any upkeep in post we need to
		if(!Superintendent.perCommandUpkeepPost(kittyCore, databaseManager))
			return;
	}
}
		 */
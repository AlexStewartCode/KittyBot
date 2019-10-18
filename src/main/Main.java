package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

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
import net.dv8tion.jda.core.audio.AudioSendHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;
import offline.Ref;
import trash.TrackScheduler;
import utils.GlobalLog;

// http://www.slf4j.org/ - this JDA logging tool has been disabled by specifying NOP implementation.
// This is the application entry point, and bot startup location!

@SuppressWarnings("unused")
public class Main extends ListenerAdapter
{
	public class GuildMusicManager
	{
		public final AudioPlayer player;
		public final TrackScheduler scheduler;

		public GuildMusicManager(AudioPlayerManager manager)
		{
			player = manager.createPlayer();
			scheduler = new TrackScheduler(player);
			player.addListener(scheduler);
		}
		
		public AudioPlayerSendHandler getSendHandler()
		{
			return new AudioPlayerSendHandler(player);
		}
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
		private TextChannel channel;
		private Guild guild;
		private GuildMusicManager manager;
		
		private SmallAudio(TextChannel channel, Guild guild, GuildMusicManager manager)
		{
			this.channel = channel;
			this.manager = manager;
			this.guild = guild;
		}
		
		@Override public void loadFailed(FriendlyException arg0) { }
		@Override public void noMatches() { }
		@Override public void playlistLoaded(AudioPlaylist arg0) { }
		
		@Override
		public void trackLoaded(AudioTrack track)
		{
			connectToFirstVoiceChannel(guild.getAudioManager());
			manager.scheduler.queue(track);
		}
		
		private void connectToFirstVoiceChannel(AudioManager audioManager)
		{
			if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect())
			{
				for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels())
				{
					audioManager.openAudioConnection(voiceChannel);
					break;
				}
			}
		}
	}
	
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
	
	public final AudioPlayerManager playerManager;
	private final Map<Long, GuildMusicManager> musicManagers;
	
	public Main()
	{
		this.musicManagers = new HashMap<>();
		
		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
	}
	
	private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild)
	{
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
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		if(event.getMessage().getContentRaw().contains("!hasan"))
		{
			final TextChannel channel = event.getChannel();
			final Guild guild = channel.getGuild();
			
			GuildMusicManager musicManager = getGuildAudioPlayer(guild);
			playerManager.loadItemOrdered(musicManager, "https://www.youtube.com/watch?v=qL-JCVA22Lo", new SmallAudio(channel, guild, musicManager));
		}
		
		super.onGuildMessageReceived(event);
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
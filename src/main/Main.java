package main;

import javax.security.auth.login.LoginException;
import core.*;
import core.benchmark.BenchmarkManager;
import core.lua.PluginManager;
import core.lua.PluginUser;
import dataStructures.KittyChannel;
import dataStructures.KittyCore;
import dataStructures.KittyGuild;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.*;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import offline.*;
import utils.GlobalLog;
import net.dv8tion.jda.core.*;
import java.util.*;

// http://www.slf4j.org/ - this JDA logging tool has been disabled by specifying NOP implementation.
// This is the application entry point, and bot startup location!

@SuppressWarnings("unused")
public class Main extends ListenerAdapter
{
	// Variables and bot specific objects
	private static KittyCore kittyCore;
	private static DatabaseManager databaseManager; 
	private static CommandEnabler commandEnabler;
	private static CommandManager commandManager;
	private static Stats stats;
	private static RPManager rpManager;
	private static PluginManager pluginManager;
	
	// Initialization and setup
	public static void main(String[] args) throws InterruptedException, LoginException, Exception
	{
		// Factory startup. The ordering is intentional.
		GlobalLog.initialize();
		databaseManager = ObjectBuilderFactory.ConstructDatabaseManager();
		commandEnabler = ObjectBuilderFactory.ConstructCommandEnabler();
		commandManager = ObjectBuilderFactory.ConstructCommandManager(commandEnabler);
		stats = ObjectBuilderFactory.ConstructStats(commandManager);
		rpManager = ObjectBuilderFactory.ConstructRPManager();
		pluginManager = ObjectBuilderFactory.ConstructPluginManager();
		
		// Bot startup
		kittyCore = ObjectBuilderFactory.ConstructKittyCore();
	}

	// When a message is sent in a server that kitty is in, this is what's called.
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		// Tweak the event as necessary
		if(!Superintendent.PreProcessSetup(event, stats))
			return;
		
		// Factory objects
		KittyUser user = ObjectBuilderFactory.ExtractUser(event);
		KittyGuild guild = ObjectBuilderFactory.ExtractGuild(event);
		KittyChannel channel = ObjectBuilderFactory.ExtractChannel(event);
		
		// Specialized uncached objects
		Response response = new Response(event, kittyCore);
		UserInput input = new UserInput(event, guild);
		
		// Tweak object construction as necessary
		if(!Superintendent.PostProcessSetup(event, user, guild, channel, response, input))
			return;
				
		// Track beans!
		user.ChangeBeans(1);
		
		// RP logging system
		RPManager.instance.addLine(channel, user, input);
		
		// Run any pre-emptive upkeep we need to
		Superintendent.PerCommandUpkeepPre();
		
		// Attempt to spin up a command. If the command doesn't exist.
		// Run plugins right before invoking the commands but after all other setup.
		if(commandManager.InvokeOnNewThread(guild, channel, user, input, response) == false)
		{
			List<String> pluginOutput = pluginManager.RunAll(input.message, user);
			
			if(pluginOutput != null && pluginOutput.size() > 0)
			{
				for(int i = 0; i < pluginOutput.size(); ++i)
				{
					if(pluginOutput.get(i).startsWith("!"))
					{
						commandManager.InvokeOnNewThread(guild, channel, user, new UserInput(pluginOutput.get(i).substring(1), guild), response);
					}
					else
					{
						response.Call(pluginOutput.get(i));
					}
				}
			}
		}
		
		// Run any upkeep in post we need to
		Superintendent.PerCommandUpkeepPost(kittyCore, databaseManager);
	}
}
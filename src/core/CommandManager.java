package core;

import java.util.*;
import java.util.Map.Entry;

import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyUser;
import dataStructures.Pair;
import dataStructures.Response;
import dataStructures.UserInput;
import utils.GlobalLog;
import utils.LogFilter;

public class CommandManager 
{
	// Variables
	private HashMap<String, Command> commands;
	private ArrayList<CommandThread> threadAccumulator;
	private CommandEnabler commandEnabler;
	private long invokeCount;
	
	// Default Constructor
	public CommandManager(CommandEnabler commandEnabler)
	{
		this.commands = new HashMap<String, Command>();
		this.threadAccumulator = new ArrayList<CommandThread>();
		this.invokeCount = 0;
		this.commandEnabler = commandEnabler; 
	}
	
	// Allows the command manager to keep track of a command. Takes a pair (the un-localized and localzied commands)
	// and the command associated with the localized commands.
	public void register(Pair<String, String> pair, Command command)
	{	
		if(pair == null || pair.Second == null)
			return;
		
		String key = pair.Second;
		
		if(key.contains(","))
		{
			
			String[] keys = key.split(",");
			register(keys, command);
			return;
		}
		
		key = key.toLowerCase();
		command.registeredNames.add(key);
		
		Command old = commands.put(key, command);
		
		if(old != null)
		{
			GlobalLog.warn(LogFilter.Core, "Writing over a command with the key " + key);
			return;
		}
		
		GlobalLog.log(LogFilter.Core, "Command registered under key " + key);
	}
	
	// Registers a command under multiple names!
	public void register(String[] keys, Command command)
	{
		for(int i = 0; i < keys.length; ++i)
			register(new Pair<String, String>(null, keys[i].trim()), command);
	}
	
	// Calls the command but on a whole new thread!
	public boolean invokeOnNewThread(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response responseContext)
	{
		// This is here to prevent spinning up a thread if this wasn't even a command.
		if(input == null || !input.isValid())
			return false;
		
		// If we haven't already split a multisplit command (or even assessed that), 
		// then verify if we even need to register the commands at all. If it's 
		// not enabled, don't register it. 
		if(input.key != null && !commandEnabler.isEnabled(input.key))
			return false;
		
		Command command = commands.get(input.key);
		if(command != null)
		{
			// Spin up a thread and begin it. The thread carries the info needed to invoke the command.
			CommandThread newThread = new CommandThread(this, input, guild, channel, user, responseContext);
			threadAccumulator.add(newThread);
			newThread.start();
			return true;
		}
		else
		{
			GlobalLog.warn(LogFilter.Command, "User " + user.name + " tried to invoke command that doesn't exist: " + input.key);
			return false;
		}
	}
	
	// Calls the command specified with the key, providing user information arguments, etc.
	void invoke(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response responseContext)
	{
		if(input == null || !input.isValid())
			return;
		
		Command command = commands.get(input.key);
		if(command != null)
		{
			++invokeCount;
			command.invoke(guild, channel, user, input, responseContext);
		}
	}
	
	// Looks up command by name. If it exists, dumps help text, otherwise returns null.
	public String getCommandHelpText(String key)
	{
		Command command = commands.get(key);
		
		if(command != null)
			return "`" + key + "`: " + command.getHelpText();
		
		return null;
	}
	
	// Returns the number of commands sent so far during this program run
	public long getInvokeCount()
	{
		return invokeCount;
	}
	
	// Returns all commands by name
	public ArrayList<Command> getAllRegisteredCommands()
	{
		ArrayList<Command> cmds = new ArrayList<Command>();
		for(Entry<String, Command> entry : commands.entrySet())
			cmds.add(entry.getValue());
		
		return cmds;
	}
	
	// Info about threads running packaged into an object
	public class ThreadData
	{
		public HashMap<Thread.State, Integer> states = new HashMap<Thread.State, Integer>();
	}
	public ThreadData dumpThreadData()
	{
		ThreadData data = new ThreadData();
		
		for(int i = 0; i < threadAccumulator.size(); ++i)
		{
			Thread.State state = threadAccumulator.get(i).getState();
			Integer num = data.states.get(state);
			
			if(num == null)
				num = 0;
			
			data.states.put(state, num + 1);
		}
		
		return data;
	}
}

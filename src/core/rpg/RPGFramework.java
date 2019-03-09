package core.rpg;

import java.util.HashMap;

import commands.rpg.RPGCommandBattleFight;
import commands.rpg.RPGCommandBattleRun;
import commands.rpg.RPGCommandExplore;
import commands.rpg.RPGCommandInfo;
import commands.rpg.RPGCommandStats;
import core.rpg.RPGInput;

// Holds the framework for the text RPG, for any number of users
public class RPGFramework
{
	// User ID to user state. Users are conceptually just an ID.
	public HashMap<String, RPGState> gameStates;
	public HashMap<String, RPGCommand> gameCommands;
	
	// Ctor
	public RPGFramework()
	{
		this.gameStates = new HashMap<String, RPGState>();
		this.gameCommands = new HashMap<String, RPGCommand>();
		
		RegisterCommand("stats", new RPGCommandStats());
		RegisterCommand("about", new RPGCommandInfo());
		RegisterCommand("info", new RPGCommandInfo());
		RegisterCommand("explore", new RPGCommandExplore());
		RegisterCommand("run", new RPGCommandBattleRun());
		RegisterCommand("fight", new RPGCommandBattleFight());
	}
	
	// Primary external
	public String Run(String userID, String inputRaw)
	{
		RPGState state = LookupState(userID);
		RPGInput input = new RPGInput(inputRaw);
		return ExecuteCommand(input.key, state, input);
	}

	// Get state for executing a command
	public RPGState LookupState(String userID)
	{
		RPGState state;
		synchronized(gameStates)
		{
			// Note: Hardcoded right now. Later: Extract.
			state = gameStates.get(userID);
			
			if(state == null)
			{
				state = new RPGState(userID);
				gameStates.put(userID, state);
			}
		}
		
		return state;
	}

	// Registers a command
	private void RegisterCommand(String commandName, RPGCommand command)
	{
		commandName = commandName.toLowerCase();
		if(gameCommands.put(commandName, command) != null)
			RPGLog.Log("Managed to register the same RPG command twice! Not ideal!");
		
		RPGLog.Log("Registered " + commandName);
	}
	
	private String ExecuteCommand(String name, RPGState state, RPGInput input)
	{
		synchronized(gameCommands)
		{
			RPGCommand command = gameCommands.get(name.toLowerCase());
			
			if(command != null && state != null)
				return command.OnRun(state,  input);
		}
		
		return null; 
	}
}

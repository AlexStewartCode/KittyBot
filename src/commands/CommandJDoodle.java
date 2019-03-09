package commands;

import core.Command;
import dataStructures.*;
import network.NetworkJDoodle;

public class CommandJDoodle extends Command 
{
	NetworkJDoodle compiler = new NetworkJDoodle();
	
	public CommandJDoodle(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return "Will compile any java code you put in! Supports Java 1.8"; }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		res.Call(compiler.compileJava(input.args));
	}
}

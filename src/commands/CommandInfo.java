package commands;

import core.Command;
import core.Localizer;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandInfo extends Command 
{
	public CommandInfo(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return Localizer.Stub("Provides some extra info about KittyBot"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		res.Call(Localizer.Stub("I'm a Java application written with JDA! Check out my repo at <https://github.com/AlexStewartCode/KittyBot>"));
	}
}
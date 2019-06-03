package commands;

import core.*;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

// A ping! Good example command.
public class CommandPing extends Command
{
	public CommandPing(KittyRole level, KittyRating rating) { super(level, rating); }

	@Override
	public String getHelpText() { return LocStrings.Stub("PingInfo"); }
	
	// Called when the command is run!
	@Override 
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		res.Call(LocStrings.Stub("PingResponse"));
	}
}

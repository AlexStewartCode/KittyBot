package commands;

import core.Command;
import core.Localizer;
import dataStructures.*;

public class CommandChangeIndicator extends Command
{
	public CommandChangeIndicator(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return Localizer.Stub("ChangeIndicatorInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		String arg = input.args.trim();
		if(arg.length() == 0)
		{
			res.Call(Localizer.Stub("ChangeIndicatorError"));
			return;
		}
		
		guild.SetCommandIndicator(arg.substring(0, 1));
		res.Call(String.format(Localizer.Stub("ChangeIndicatorChanged"), guild.GetCommandIndicator()));
	}
}

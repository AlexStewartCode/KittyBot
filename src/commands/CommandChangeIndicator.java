package commands;

import core.Command;
import core.LocStrings;
import dataStructures.*;

public class CommandChangeIndicator extends Command
{
	public CommandChangeIndicator(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("ChangeIndicatorInfo"); }
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		String arg = input.args.trim();
		if(arg.length() == 0)
		{
			res.call(LocStrings.stub("ChangeIndicatorError"));
			return;
		}
		
		String indicator = arg.substring(0, 1);
		if(indicator.charAt(0) == '\n' || indicator.charAt(0) == '\t' || indicator.charAt(0) == '\r')
		{
			res.call(LocStrings.Lookup("ChangeIndicatorError"));
			return;
		}
		
		guild.SetCommandIndicator(indicator);
		res.call(String.format(LocStrings.stub("ChangeIndicatorChanged"), guild.GetCommandIndicator()));
	}
}

package commands.dew;

import core.Command;
import core.LocStrings;
import core.SubCommandFramework;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandDew extends Command  {
	
	SubCommandFramework framework = new SubCommandFramework();

	@Override
	public String getHelpText() { return LocStrings.stub("DewInfo"); }
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res) 
	{
		framework.run(guild, channel, user, input).Call(res);
	}
	
	public CommandDew(KittyRole level, KittyRating rating) 
	{
		super(level, rating);
		
		framework.addCommand("start", new CommandDewStart(KittyRole.Dev, KittyRating.Safe));
	}
	
}

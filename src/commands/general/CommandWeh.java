package commands.general;

import core.Command;
import core.LocStrings;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandWeh extends Command {

	public String getHelpText() { return LocStrings.stub("WehInfo"); }
	
	public CommandWeh(KittyRole roleLevel, KittyRating contentRating) {
		super(roleLevel, contentRating);
		// TODO Auto-generated constructor stub
	}

	@Override
	// The format for reutrning an emoji is: <:name:ID#>
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		res.send("<:weh:548301646929723393>");
	}

}

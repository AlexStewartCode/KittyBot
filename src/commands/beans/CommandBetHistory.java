package commands.beans;

import java.io.File;

import core.Command;
import core.LocStrings;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;
import utils.GlobalLog;
import utils.LogFilter;

public class CommandBetHistory extends Command
{
	public CommandBetHistory(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("BetHistoryInfo"); }
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		// Send primary response
		res.send(String.format(LocStrings.stub("BetHistoryOutput"), guild.beans.get()));
		
		// Look up extra information to see if we ahve a flair image
		String optional = LocStrings.stub("BetHistoryOptionalPNGPath");
		if(optional != null && optional.length() > 0)
		{
			File responseImage = new File(optional);
			if(responseImage.exists())
			{
				res.sendFile(responseImage, ".png");
			}
			else
			{
				GlobalLog.warn(LogFilter.Command, "Optional PNG image for bet history was specified, but the file could not be found.");
			}
		}
	}
}
package commands;

import core.Command;
import core.LocStrings;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

// Displays Patreon sponsors that donate to help pay for hosting and stuff!
public class CommandSponsors extends Command
{
	public CommandSponsors(KittyRole level, KittyRating rating) { super(level, rating); }

	@Override
	public String HelpText() { return LocStrings.Stub("SponsorInfo"); }
	
	@Override 
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		String out = "Thanks to our Patreon sponsors!\n";
		out += "\n"; //...
		out += LocStrings.Stub("SponsorSupportLink"); // Consideration: "Want to get your name on this command and support development? Support us at <https://www.patreon.com/Kittybot>!\n"
		
		res.Call(out);
	}
}


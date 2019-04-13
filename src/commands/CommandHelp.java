package commands;

import core.*;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

// TODO: Convert this to a per-command help string! This lets us do all sorts of neat stuff,
// mostly tho it lets us construct this on the fly or by hand for a specific command!
public class CommandHelp extends Command
{
	public CommandHelp(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return Localizer.Stub("Lets you look up specific commands, or get a link to a list of all commands."); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{ 
		String help = Stats.instance.GetHelpText(input.args);
		
		if(help == null)
		{
			help = Localizer.Stub("You can get help with a specific command by typing `!help command`!\nGeneral Commands: `boop, roll, choose, help, info, vote, results, showpoll, wolfram, cplus, java, beans, role, bet, yeet`");
		}
		else
		{
			help = "`" + input.args + "`: " + help;
		}
		
		res.Call(help);
	}
	
}
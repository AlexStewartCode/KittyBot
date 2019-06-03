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

public class CommandGuildRoleAdd extends Command
{
	public CommandGuildRoleAdd(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("GuildRoleAddInfo"); };
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		String role = input.args.split(" ")[0];
		if(guild.roleList.contains(role))
		{
			if(guild.control.addRole(user.discordID, role))
			{
				res.Call(String.format(LocStrings.stub("GuildRoleAddSuccess"), role, user.name));
			}
			else
			{
				res.Call(String.format(LocStrings.stub("GuildRoleAddFailure"), role, user.name));
			}
		}
		else
		{
			res.Call(String.format(LocStrings.stub("GuildRoleAddNotAllowed"), role));
		}
	}
}
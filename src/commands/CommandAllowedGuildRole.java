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

public class CommandAllowedGuildRole extends Command
{
	public CommandAllowedGuildRole(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return LocStrings.Stub("AllowedGuildRoleInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		String role = input.args.split(" ")[0];
		if(guild.allowedRole.contains(role))
		{
			res.Call(LocStrings.Stub("AllowedGuildRoleDuplicate"));
		}
		else
		{
			guild.allowedRole.add(role);
			res.Call(String.format(LocStrings.Stub("AllowedGuildRoleSuccess"), role));
		}
	}
}
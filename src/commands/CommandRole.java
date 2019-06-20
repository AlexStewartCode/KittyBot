package commands;

import core.Command;
import core.LocStrings;
import dataStructures.*;

public class CommandRole extends Command
{
	public CommandRole (KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("RoleInfo"); }
	
	@Override 
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(input.args.isEmpty())
		{
			res.call(LocStrings.stub("RoleStandardResponse") + " " + user.GetRole().name() + "!");
			return;
		}
		
		if(user.GetRole().getValue() < KittyRole.Admin.getValue())
		{
			res.call(String.format(LocStrings.stub("RoleError"), KittyRole.Admin.toString()));
			return;
		}
		
		KittyRole newRole;
		switch(input.args.split(" ")[0].toLowerCase())
		{
			case "blacklist":
				newRole = KittyRole.Blacklisted;
				break;
				
			case "general":
				newRole = KittyRole.General;
				break;
				
			case "mod":
				newRole = KittyRole.Mod;
				break;
				
			case "admin":
				newRole = KittyRole.Admin;
				break; 
				
			default:
				res.call(LocStrings.stub("RoleNeededRole"));
				return;
		}
		String users = "";
		for(int i = 0; i < input.mentions.length; i++)
		{
			input.mentions[i].ChangeRole(newRole); 
			users += input.mentions[i].name + " ";
		}
		
		res.call(String.format(LocStrings.stub("RoleChanged"), users, newRole.name()));
	}
}

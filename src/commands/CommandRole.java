package commands;

import core.Command;
import dataStructures.*;

public class CommandRole extends Command
{
	public CommandRole (KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return "Will show the current role you have, Admins can change others roles with input of 'role x @y' with x being blacklist, general, mod, or admin. Blacklist will not allow the user to interact with kitty anymore, general mod and admin will give the user access to those commands."; }
	
	@Override 
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(input.args.isEmpty())
		{
			res.Call("Your role is " + user.GetRole().name() + "!");
			return;
		}
		
		if(user.GetRole().getValue() < KittyRole.Admin.getValue())
		{
			res.Call("You aren't allowed to do that! You must have the KittyRole '" + KittyRole.Admin.toString() + "' or higher!");
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
				res.Call("Please enter `general`, `mod`, or `admin`!");
				return;
		}
		String users = "";
		for(int i = 0; i < input.mentions.length; i++)
		{
			input.mentions[i].ChangeRole(newRole); 
			users += input.mentions[i].name + " ";
		}
		
		res.Call("Changed " + users + " to role `" + newRole.name() + "`!");
	}
}

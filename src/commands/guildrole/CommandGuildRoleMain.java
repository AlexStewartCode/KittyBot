package commands.guildrole;

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

public class CommandGuildRoleMain extends Command
{
	SubCommandFramework framework = new SubCommandFramework();
	public CommandGuildRoleMain(KittyRole level, KittyRating rating) 
	{ 
		super(level, rating); 
		framework.addCommand("add", new CommandGuildRoleAdd (KittyRole.General, KittyRating.Safe));
		framework.addCommand("remove", new CommandGuildRoleRemove(KittyRole.General, KittyRating.Safe));
		framework.addCommand("allowed", new CommandGuildRoleAllowed(KittyRole.Admin, KittyRating.Safe));
		framework.addCommand("unallowed", new CommandGuildRoleNotAllowed(KittyRole.Admin, KittyRating.Safe));
	}
	
	@Override
	public String getHelpText() { return LocStrings.stub("GuildRoleInfo"); }
	
	
	
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		framework.run(guild, channel, user, input.args).Call(res);
	}
}

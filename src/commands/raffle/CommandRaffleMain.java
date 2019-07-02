package commands.raffle;

import core.Command;
import core.SubCommandFramework;
import dataStructures.*;

public class CommandRaffleMain extends Command 
{
	SubCommandFramework framework = new SubCommandFramework();
	public CommandRaffleMain(KittyRole level, KittyRating rating) 
	{ 
		super(level, rating); 
		framework.addCommand("start", new CommandRaffleStart(KittyRole.Mod, KittyRating.Safe));
		framework.addCommand("spin", new CommandRaffleSpin(KittyRole.Mod, KittyRating.Safe));
		framework.addCommand("end", new CommandRaffleEnd(KittyRole.Mod, KittyRating.Safe));
		framework.addCommand("join", new CommandRaffleJoin(KittyRole.General, KittyRating.Safe));
	}
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res) 
	{
		framework.run(guild, channel, user, input.args).Call(res);
	}
}
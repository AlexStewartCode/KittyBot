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

public class CommandTradeBeans extends Command
{
	public CommandTradeBeans(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("TradeBeansInfo"); }
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(input.mentions == null)
		{
			res.call(LocStrings.stub("TradeBeansNoTargetError"));
			return;
		}
		
		int beans = 0;
		try {
			beans = Integer.parseInt(input.args.split(" ")[0]);
		}
		catch (NumberFormatException e)
		{
			res.call(LocStrings.stub("TradeBeansIntParseError"));
			return;
		}
		
		if(user.GetBeans() < beans)
		{
			res.call(LocStrings.stub("TradeBeansNotEnoughError"));
			return;
		}
		
		if(beans < 0)
		{
			res.call(String.format(LocStrings.stub("TradeBeansStealingBeans"), user.name));
			user.ChangeBeans(-10);
			return; 
		}
		
		input.mentions[0].ChangeBeans(beans);
		user.ChangeBeans(-beans);
		res.call(String.format(LocStrings.stub("TradeBeansSuccess"), user.name, input.mentions[0].name, beans));
	}
}

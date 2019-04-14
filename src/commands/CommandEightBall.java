package commands;

import core.Command;
import core.Localizer;
import dataStructures.*;

public class CommandEightBall extends Command
{
	String [] answers = 
	{ 
			  Localizer.Stub("EightBallYes1") 
			, Localizer.Stub("EightBallYes2")
			, Localizer.Stub("EightBallYes3")
			, Localizer.Stub("EightBallYes4")
			, Localizer.Stub("EightBallYes5")
			, Localizer.Stub("EightBallYes6")
			, Localizer.Stub("EightBallYes7")
			, Localizer.Stub("EightBallYes8")
			, Localizer.Stub("EightBallYes9")
			, Localizer.Stub("EightBallYes10")
			
			, Localizer.Stub("EightBallMaybe1")
			, Localizer.Stub("EightBallMaybe2")
			, Localizer.Stub("EightBallMaybe3")
			, Localizer.Stub("EightBallMaybe4")
			, Localizer.Stub("EightBallMaybe5")
			
			, Localizer.Stub("EightBallNo1")
			, Localizer.Stub("EightBallNo2")
			, Localizer.Stub("EightBallNo3")
			, Localizer.Stub("EightBallNo4")
			, Localizer.Stub("EightBallNo5")
	};
	
	public CommandEightBall(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return Localizer.Stub("EightBallInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(input.args.trim().length() < 1)
			res.Call(Localizer.Stub("EightBallError"));
		
		res.Call(answers[(int) (Math.random()*answers.length)]);
	}
}

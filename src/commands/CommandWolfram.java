package commands;

import java.io.File;
import java.io.IOException;
import core.Command;
import core.Localizer;
import dataStructures.*;
import network.*;
import utils.ImageUtils;

public class CommandWolfram extends Command 
{
	NetworkWolfram searcher = new NetworkWolfram();
	
	public CommandWolfram(KittyRole level, KittyRating rating) { super(level, rating);}
	
	@Override
	public String HelpText() { return Localizer.Stub("WolframInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(input.args == null || input.args.trim().length() == 0)
			res.Call(Localizer.Stub("WolframNoArgs"));
		
		try 
		{
			File pic = new File(searcher.getWolfram(input.args));
			res.CallFile(pic, "png");
			ImageUtils.BlockingFileDelete(pic);
		} 
		catch (IOException e) 
		{
			res.Call(Localizer.Stub("WolframError"));
		}
	}
}
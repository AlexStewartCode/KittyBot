package commands;

import java.io.File;
import java.io.IOException;
import core.Command;
import dataStructures.*;
import network.*;
import utils.ImageUtils;

public class CommandWolfram extends Command 
{
	NetworkWolfram searcher = new NetworkWolfram();
	
	public CommandWolfram(KittyRole level, KittyRating rating) { super(level, rating);}
	
	@Override
	public String HelpText() { return "Will query wolframalpha with your question and give a full image output of the answer"; }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(input.args == null || input.args.trim().length() == 0)
			res.Call("You need to provide some arguments!");
		
		try 
		{
			File pic = new File(searcher.getWolfram(input.args));
			res.CallFile(pic, "png");
			ImageUtils.BlockingFileDelete(pic);
		} 
		catch (IOException e) 
		{
			res.Call("Something went wrong!");
		}
	}
}
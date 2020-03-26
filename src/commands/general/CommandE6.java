package commands.general;

import core.Command;
import core.LocStrings;
import dataStructures.*;
import network.NetworkE6;

public class CommandE6 extends Command
{
	NetworkE6 searcher = new NetworkE6();
	KittyEmbed response;
	GenericImage image; 
	
	public CommandE6(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText()
	{
		String toRet = "Will search e6"; 
		toRet += "21 for the tags entered, if KittyBot's content filter allows it";
		return toRet;
	}
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(guild.contentRating == KittyRating.Filtered)
		{
			image = searcher.getE6(input.args + " rating:safe");
			try
			{
				response = image.output();
			}
			catch(Exception e)
			{				
				res.send(LocStrings.stub("E6Blacklisted"));
				return;
			}
			try 
			{
				if(response.bodyImageURL == null)
				{
					Exception e = new Exception();
					throw e; 
				}
				res.send(response);
			}
			catch(Exception e)
			{
				res.send(LocStrings.stub("E6Blacklisted"));
			}
		}
		else
		{
			if(input.args == null || input.args.length() == 0)
				res.send("E6NoSearchError");
			else
			{
				try 
				{
					response = searcher.getE6(input.args).output();
					res.send(response);
				}
				catch(Exception e)
				{
					res.send(LocStrings.stub("E6Blacklisted"));
				}
			}
		}
	}
}